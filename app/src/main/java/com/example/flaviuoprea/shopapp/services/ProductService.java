package com.example.flaviuoprea.shopapp.services;

import com.example.flaviuoprea.shopapp.data.model.Product;

import java.util.List;

import retrofit2.http.PUT;
import rx.Completable;
import rx.Observable;
import retrofit2.http.GET;

/**
 * Created by flaviuoprea on 12/17/17.
 */

public interface ProductService {
    @GET("/products")
    Observable<List<Product>> getProducts();

    @PUT("/products")
    Completable updateProduct();

    Observable<List<Product>> getProductsOffline();

}
