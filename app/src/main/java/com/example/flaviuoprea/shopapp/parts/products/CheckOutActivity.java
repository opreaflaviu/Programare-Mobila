package com.example.flaviuoprea.shopapp.parts.products;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.flaviuoprea.shopapp.R;
import com.example.flaviuoprea.shopapp.data.model.ProductAdapter;
import com.example.flaviuoprea.shopapp.data.model.ShoppingCart;

public class CheckOutActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    private ShoppingCart mShoppingCart;
    private AppCompatTextView mTotalPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        mShoppingCart = ShoppingCart.newInstance();
        mTotalPriceView = findViewById(R.id.total_price_checkout);
        mRecyclerView = findViewById(R.id.recycler_view_products_checkout);
        configViews();
    }

    private void configViews() {
        int total = mShoppingCart.getmTotal();
        String totalPrice = "Total: " + total;
        mTotalPriceView.setText(totalPrice);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProductAdapter(this, true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItems(mShoppingCart.getmProductList());
    }
}
