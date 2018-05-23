package com.example.flaviuoprea.shopapp.di;
import com.example.flaviuoprea.shopapp.services.ProductService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by flaviuoprea on 12/17/17.
 */

@Module
public class ApiModule {


    @Provides
    @CustomScope
    ProductService provideProductService(Retrofit retrofit){
        return retrofit.create(ProductService.class);
    }

}
