package com.example.flaviuoprea.shopapp.parts.products;

import com.example.flaviuoprea.shopapp.data.model.Product;

import java.util.List;

import rx.Observable;

/**
 * Created by flaviuoprea on 12/30/17.
 */

public interface ProductsView {
    void onCompleted();
    void onError(String message);
    void onProducts(List<Product> productList);
    Observable<List<Product>> getProducts();
}
