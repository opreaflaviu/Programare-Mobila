package com.example.flaviuoprea.shopapp.parts.products;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.flaviuoprea.shopapp.R;
import com.example.flaviuoprea.shopapp.ShopApp;
import com.example.flaviuoprea.shopapp.data.model.Product;
import com.example.flaviuoprea.shopapp.data.model.ProductAdapter;
import com.example.flaviuoprea.shopapp.data.model.ShoppingCart;
import com.example.flaviuoprea.shopapp.services.ProductService;
import com.example.flaviuoprea.shopapp.utils.SystemUtils;

import java.util.List;
import javax.inject.Inject;

import rx.Observable;


public class ProductsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProductsView {

    @Inject
    ProductService mProductService;

    private AppCompatTextView totalPriceView;
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private ProductsPresenter mProductsPresenter;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude,longitude;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resolveDependency();

        totalPriceView = findViewById(R.id.total_price);

        mRecyclerView = findViewById(R.id.recycler_view_products);
        configViews();

        mProductsPresenter = new ProductsPresenter(this, getActivityContext());
        mProductsPresenter.onCreate();

        String totalPrice = "Total: 0";
        totalPriceView.setText(totalPrice);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void resolveDependency() {
        ((ShopApp) getApplication())
                .getApiComponent()
                .inject(this);
    }

    private void configViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProductAdapter(this, false);
        mAdapter.setOnItemClickListener(shoppingCart -> {
            String totalPrice = "Total: " + shoppingCart.getmTotal();
            totalPriceView.setText(totalPrice);
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mProductsPresenter.onResume();
        if (checkConnection()){
            mProductsPresenter.fetchProductsOfline();
            return;
        }
        mProductsPresenter.fetchProducts();
    }

    private boolean checkConnection(){
        SystemUtils systemUtils = new SystemUtils(this);
        return systemUtils.isNetworkUnavailable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mProductsPresenter.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           Intent intent = new Intent(this, CheckOutActivity.class);
           startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_location) {
            enableLocation();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void enableLocation(){
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(ProductsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (ProductsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ProductsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                Toast.makeText(this, "L " + longitude + " " + lattitude, Toast.LENGTH_SHORT).show();
                openMaps(lattitude, longitude);

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                Toast.makeText(this, "L1 " + longitude + " " + lattitude, Toast.LENGTH_SHORT).show();
                openMaps(lattitude, longitude);

            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Toast.makeText(this, "L2 " + longitude + " " + lattitude, Toast.LENGTH_SHORT).show();
                openMaps(lattitude, longitude);


            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void openMaps(String lattitude, String longitude){
        Uri gmmIntentUri = Uri.parse("geo:" + lattitude+"," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    protected void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) ->
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("No", (dialog, id) ->
                        dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }



    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProducts(List<Product> productList) {
        mAdapter.setItems(productList);
    }

    @Override
    public Observable<List<Product>> getProducts() {
        return mProductService.getProducts();
    }


    public Context getActivityContext() {
        return ProductsActivity.this;
    }

}
