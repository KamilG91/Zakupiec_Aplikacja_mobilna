package com.example.myapplication.model;


import java.util.ArrayList;
import java.util.Date;

public class ShoppingList {
    private int id;
    private String name;
    private String dateTime;
    //private ArrayList<Product> products;

    public ShoppingList(int id, String name, String dateTime)  {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        //products = new ArrayList<>();
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
/*
    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProduct(Product product) {
        products.remove(product);
    }
    */
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
