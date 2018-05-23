package com.example.flaviuoprea.shopapp.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.flaviuoprea.shopapp.data.model.Product;
import com.example.flaviuoprea.shopapp.data.model.User;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flaviuoprea on 12/5/17.
 */

public class DatabaseManager {
    private static DatabaseManager mDBManager;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private DatabaseManager(Context context){
        mContext = context;
        mDBHelper = new DatabaseHelper(mContext);
    }

    public static DatabaseManager getInstance(Context context) {
        if (mDBManager == null){
            mDBManager = new DatabaseManager(context);
        }
        return mDBManager;
    }

    public void closeDatabase(){
        if (mDatabase != null){
            mDatabase.close();
        }
    }

    public void clearDatabaseProducts(){
        mDatabase = mDBHelper.getWritableDatabase();
        mDatabase.delete(DatabaseContract.TABLE_NAME_PRODUCTS, null, null);
    }

    public void insertProducts(List<Product> productList){
        mDatabase = mDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (Product product : productList) {
            contentValues.put(DatabaseContract.COL_1_PRODUCTS, product.getId());
            contentValues.put(DatabaseContract.COL_2_PRODUCTS, product.getName());
            contentValues.put(DatabaseContract.COL_3_PRODUCTS, product.getPret());
            contentValues.put(DatabaseContract.COL_4_PRODUCTS, product.getImagine());
            mDatabase.insert(DatabaseContract.TABLE_NAME_PRODUCTS, null, contentValues);
        }
    }

    public List<Product> getProducts(){
        List<Product> productsList = new ArrayList<>();
        String[] projection = {DatabaseContract.COL_1_PRODUCTS, DatabaseContract.COL_2_PRODUCTS, DatabaseContract.COL_3_PRODUCTS, DatabaseContract.COL_4_PRODUCTS};

        mDatabase = mDBHelper.getWritableDatabase();
        Cursor cursor = mDatabase.query(DatabaseContract.TABLE_NAME_USERS, projection, null, null, null, null, null);

        if (cursor != null) {
            int columnIndexProductID = cursor.getColumnIndex(DatabaseContract.COL_1_PRODUCTS);
            int columnIndexProductName = cursor.getColumnIndex(DatabaseContract.COL_2_PRODUCTS);
            int columnIndexProductPrice = cursor.getColumnIndex(DatabaseContract.COL_3_PRODUCTS);
            int columnIndexProductImage = cursor.getColumnIndex(DatabaseContract.COL_4_PRODUCTS);

            while (cursor.moveToNext()){
                int id = cursor.getInt(columnIndexProductID);
                String name = cursor.getString(columnIndexProductName);
                int price = cursor.getInt(columnIndexProductPrice);
                String image = cursor.getString(columnIndexProductImage);

                Product product = new Product(id, name, price, image);
                productsList.add(product);
            }
            cursor.close();
        }
        return productsList;
    }
}
