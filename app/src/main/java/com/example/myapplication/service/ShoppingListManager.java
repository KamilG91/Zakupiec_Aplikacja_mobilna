package com.example.myapplication.service;

import android.content.Context;

import com.example.myapplication.model.Product;
import com.example.myapplication.model.ShoppingList;
import com.example.myapplication.service.DAO.DatabaseAccessor;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingListManager {
    private DatabaseAccessor _dbManager;
    private Context _context;

    public ShoppingListManager(Context context) {
        _dbManager = new DatabaseAccessor(context);
        _context = context;
    }

    public ArrayList<ShoppingList> getMyShoppingLists() {
        return _dbManager.getAllShoppingLists();
    }

    public ArrayList<ShoppingList> getMySortedShoppingLists() {
        ArrayList<ShoppingList> shoppingLists = getMyShoppingLists();
        Collections.sort(shoppingLists, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        return shoppingLists;
    }
    public ShoppingList getShoppingListWithId(int id) {
        return _dbManager.getShoppingListWithId(id);
    }
    public ShoppingList getLastShoppingList() {
        return  getMySortedShoppingLists().get(0);
    }
    public int addShoppingList (String name, String date) {
        return _dbManager.addShoppingList(name, date);
    }


    public void deleteShoppingList (int shoppingListId) {
        _dbManager.deleteShoppingList(shoppingListId);
    }
    public ArrayList<String> getAllShoppingListNames(){
        return _dbManager.getAllShoppingListsNames();
    }
    public ArrayList<Product> getAllProductsForShoppingList(int shoppingListID) {
        ArrayList<Product> products = _dbManager.getAllProductsForShoppingList(shoppingListID);
        return products;
    }


    public Product getProductWithId(int id) {
        return _dbManager.getProductWithId(id);
    }
    public int addProduct(@NotNull Product product) {
        return _dbManager.addProduct(product.getName(), product.getQuantity(),product.getUnit(),product.getShoppingListID());
    }

    public void deleteProduct(Product product) {
        _dbManager.deleteProduct(product.getId());
    }

}
