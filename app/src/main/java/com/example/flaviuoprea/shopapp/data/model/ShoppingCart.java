package com.example.flaviuoprea.shopapp.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Flaviu on 1/28/2018.
 */

public class ShoppingCart {
    private HashMap<String, Integer> mCart;
    private List<Product> mProductList;
    private int mTotal;

    private static ShoppingCart mShoppingCartInstance;

    private ShoppingCart(){
        mCart = new HashMap<>();
        mProductList = new ArrayList<>();
        mTotal = 0;
    }

    public static ShoppingCart newInstance(){
        if (mShoppingCartInstance == null){
            mShoppingCartInstance = new ShoppingCart();
        }
        return mShoppingCartInstance;
    }

    private boolean findProduct(Product product){
        return mCart.containsKey(product.getName());
    }

    private boolean findProductInList(Product product){
        return mProductList.contains(product);
    }

    public void addProduct(Product product){
        addProductInList(product);
        if (findProduct(product)){
            int cantitate = mCart.get(product.getName());
            mCart.put(product.getName(), cantitate+1);
        } else {
            mCart.put(product.getName(), 1);
        }
        mTotal += product.getPret();
    }

    private void addProductInList(Product product){
        if (!findProductInList(product)){
            mProductList.add(product);
        }
    }

    public void removeProduct(Product product){
        removeProductFromList(product);
        if (findProduct(product)) {
            int cantitate = mCart.get(product.getName());
            if (cantitate == 1){
                mCart.remove(product.getName());
            } else {
                mCart.put(product.getName(), cantitate - 1);
            }
            mTotal -= product.getPret();
        }
    }

    private void removeProductFromList(Product product){
        if (findProductInList(product)) {
            mProductList.remove(product);
        }
    }

    public int getmTotal(){
        return mTotal;
    }

    public HashMap getmCart(){
        return mCart;
    }

    public List<Product> getmProductList(){
        return mProductList;
    }
}
