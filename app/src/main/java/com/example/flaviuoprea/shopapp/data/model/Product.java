package com.example.flaviuoprea.shopapp.data.model;


import com.google.gson.annotations.Expose;

/**
 * Created by flaviuoprea on 12/16/17.
 */
public class Product {
    @Expose
    private int id;

    @Expose
    private String name;

    @Expose
    private int pret;

    @Expose
    private String imagine;

    public Product(){
    }

    public Product(int id, String name, int pret, String imagine) {
        this.id = id;
        this.name = name;
        this.pret = pret;
        this.imagine = imagine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getImagine() {
        return imagine;
    }

    public void setImagine(String imagine) {
        this.imagine = imagine;
    }

    @Override
    public String toString() {
        return + id + " " + name + " " + pret + " " + imagine;
    }
}
