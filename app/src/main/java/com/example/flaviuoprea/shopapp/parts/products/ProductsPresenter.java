package com.example.flaviuoprea.shopapp.parts.products;

import android.content.Context;

import com.example.flaviuoprea.shopapp.data.database.DatabaseManager;
import com.example.flaviuoprea.shopapp.data.model.Product;
import com.example.flaviuoprea.shopapp.presenter.BasePresenter;

import java.util.List;

import rx.Observer;

/**
 * Created by flaviuoprea on 12/30/17.
 */

public class ProductsPresenter extends BasePresenter implements Observer<List<Product>> {

    private ProductsView mProductView;

    DatabaseManager mDatabaseManager;

    public ProductsPresenter(ProductsView productView, Context context) {
        this.mProductView = productView;
        mDatabaseManager = DatabaseManager.getInstance(context);
    }

    @Override
    public void onCompleted() {
        mProductView.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mProductView.onError(e.getMessage());
    }

    @Override
    public void onNext(List<Product> productList) {
        mDatabaseManager.clearDatabaseProducts();
        mDatabaseManager.insertProducts(productList);
        mProductView.onProducts(productList);

    }


    public void fetchProducts() {
        unSubscribeAll();
        subscribe(mProductView.getProducts(), ProductsPresenter.this);
    }


    public void fetchProductsOfline() {
        List<Product> productsList = mDatabaseManager.getProducts();
        mProductView.onProducts(productsList);

    }
}
