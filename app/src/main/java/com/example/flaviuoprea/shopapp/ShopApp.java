package com.example.flaviuoprea.shopapp;

import android.app.Application;

import com.example.flaviuoprea.shopapp.di.ApiComponent;
import com.example.flaviuoprea.shopapp.di.ApiModule;
import com.example.flaviuoprea.shopapp.di.DaggerApiComponent;
import com.example.flaviuoprea.shopapp.di.DaggerNetworkComponent;
import com.example.flaviuoprea.shopapp.di.NetworkComponent;
import com.example.flaviuoprea.shopapp.di.NetworkModules;
import com.example.flaviuoprea.shopapp.utils.Constant;

/**
 * Created by flaviuoprea on 12/17/17.
 */

public class ShopApp extends Application {

    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {

        resolveDependency();
        super.onCreate();
    }

    private void resolveDependency() {
        mApiComponent = DaggerApiComponent
                .builder()
                .networkComponent(getNetworComponent())
                .build();
    }

    public NetworkComponent getNetworComponent() {
       return DaggerNetworkComponent
               .builder()
               .networkModules(new NetworkModules(Constant.BASE_URL))
               .build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
