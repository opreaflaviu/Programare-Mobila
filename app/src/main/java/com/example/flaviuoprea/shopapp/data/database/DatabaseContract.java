package com.example.flaviuoprea.shopapp.data.database;

import android.provider.BaseColumns;

/**
 * Created by flaviuoprea on 12/5/17.
 */

public interface DatabaseContract extends BaseColumns{

    String DATABASE_NAME = "ShopApp.db";
    String TABLE_NAME_USERS = "TABLE_NAME_USERS";
    String TABLE_NAME_PRODUCTS = "TABLE_NAME_USERS";

    String COL_1_USERS = "USERNAME";
    String COL_2_USERS = "PASSWORD";
    String CREATE_DATABASE_USERS_QUERY = "CREATE TABLE " + TABLE_NAME_USERS
            + " ("
            + COL_1_USERS + " TEXT PRIMARY KEY, "
            + COL_2_USERS + " TEXT"
            + ")";

    String COL_1_PRODUCTS = "ID";
    String COL_2_PRODUCTS = "NAME";
    String COL_3_PRODUCTS = "PRICE";
    String COL_4_PRODUCTS = "IMAGE";
    String CREATE_DATABASE_PRODUCTS_QUERY = "CREATE TABLE " + TABLE_NAME_USERS
            + " ("
            + COL_1_PRODUCTS + " INTEGER PRIMARY KEY, "
            + COL_2_PRODUCTS + " TEXT, "
            + COL_3_PRODUCTS + " INTEGER, "
            + COL_4_PRODUCTS + " TEXT"
            + ")";



}
