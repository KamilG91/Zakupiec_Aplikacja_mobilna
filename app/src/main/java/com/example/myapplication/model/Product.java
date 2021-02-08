package com.example.myapplication.model;

public class Product {
    private int id;
    private String name;
    private double quantity;
    private String unit;
    private int shoppingListID;
    public Product(int id, String name, double quantity, String unit, int shoppingListID) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.shoppingListID = shoppingListID;
    }
    public Product(String name, double quantity, String unit, int shoppingListID) {
        this.id = 0;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.shoppingListID = shoppingListID;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getShoppingListID() {
        return shoppingListID;
    }

    public void setShoppingListID(int shoppingListID) {
        this.shoppingListID = shoppingListID;
    }
}
