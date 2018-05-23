package com.example.flaviuoprea.shopapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Flaviu on 1/29/2018.
 */

public class SystemUtils {
    private final ConnectivityManager mConnectivityManager;


    public SystemUtils(Context appContext) {
        mConnectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isNetworkUnavailable() {
        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        return activeNetwork == null || !activeNetwork.isConnectedOrConnecting();
    }
}
