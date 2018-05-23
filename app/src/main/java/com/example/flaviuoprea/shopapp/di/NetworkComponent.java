package com.example.flaviuoprea.shopapp.di;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by flaviuoprea on 12/17/17.
 */
@Singleton
@Component(modules = NetworkModules.class)
public interface NetworkComponent {
    Retrofit retrofit();
}
