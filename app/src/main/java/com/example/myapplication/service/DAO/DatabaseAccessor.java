package com.example.myapplication.service.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.model.Product;
import com.example.myapplication.model.ShoppingList;

import java.util.ArrayList;


public class DatabaseAccessor extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "zakupiec_database";
    private static final int DATABASE_VERSION = 20;
    private static final String TABLE_SHOPPING_LISTS = "shopping_lists";
    private static final String TABLE_PRODUCTS = "shopping_list_products";

    //Commons
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";

    //shopping list products specific
    private static final String KEY_SHOPPING_LIST_ID = "listID";
    private static final String KEY_PRODUCT_NAME = "product_name";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_QUANTITY = "product_quantity";
    private static final String KEY_PRODUCT_UNIT = "product_unit";

    private static final String CREATE_TABLE_SHOPPING_LISTS = "CREATE TABLE " + TABLE_SHOPPING_LISTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, "
            + KEY_DATE + " TEXT);";

    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_PRODUCT_NAME + " TEXT, "
            + KEY_SHOPPING_LIST_ID + " INTEGER, "
            + KEY_PRODUCT_QUANTITY + " REAL,"
            + KEY_PRODUCT_UNIT + " TEXT);";



    public DatabaseAccessor(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHOPPING_LISTS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SHOPPING_LISTS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_PRODUCTS + "'");
        onCreate(db);
    }
    //LIST
    public ShoppingList getShoppingListWithId(int id) {
        ShoppingList shoppingList = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SHOPPING_LISTS + " WHERE " + KEY_ID + "=" + id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            shoppingList = new ShoppingList(
                    c.getInt(c.getColumnIndex(KEY_ID)),
                    c.getString(c.getColumnIndex(KEY_NAME)),
                    c.getString(c.getColumnIndex(KEY_DATE)));
        }
        return shoppingList;

    }
    public ArrayList<ShoppingList> getAllShoppingLists() {
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_SHOPPING_LISTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                ShoppingList shoppingList = new ShoppingList(
                        c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getString(c.getColumnIndex(KEY_NAME)),
                        c.getString(c.getColumnIndex(KEY_DATE)));
                shoppingLists.add(shoppingList);
            } while (c.moveToNext());
        }
        return shoppingLists;
    }
    public ArrayList<String> getAllShoppingListsNames() {
        ArrayList<String> shoppingLists = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_NAME +" FROM " + TABLE_SHOPPING_LISTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                shoppingLists.add(c.getString(c.getColumnIndex(KEY_NAME)));
            } while (c.moveToNext());
        }
        return shoppingLists;
    }
    public void deleteShoppingList(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteAllProductsForShoppingList(id);
        db.delete(TABLE_SHOPPING_LISTS, KEY_ID + " = ?",new String[]{String.valueOf(id)});
    }

    public int addShoppingList(String name, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_DATE, date);
        return (int) db.insertWithOnConflict(TABLE_SHOPPING_LISTS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    //PRODUCTS

    public int addProduct(String name, double product_quantity, String unit, int listID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_NAME, name);
        values.put(KEY_PRODUCT_QUANTITY, product_quantity);
        values.put(KEY_PRODUCT_UNIT, unit);
        values.put(KEY_SHOPPING_LIST_ID, listID);
        return (int) db.insertWithOnConflict(TABLE_PRODUCTS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    public Product getProductWithId(int id){
        Product product = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS + " WHERE " + KEY_PRODUCT_ID + "=" + id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            product = new Product(
                    c.getInt(c.getColumnIndex(KEY_PRODUCT_ID)),
                    c.getString(c.getColumnIndex(KEY_PRODUCT_NAME)),
                    c.getDouble(c.getColumnIndex(KEY_PRODUCT_QUANTITY)),
                    c.getString(c.getColumnIndex(KEY_PRODUCT_UNIT)),
                    c.getInt(c.getColumnIndex(KEY_SHOPPING_LIST_ID)));
        }
        return product;
    }
    public ArrayList<Product> getAllProductsForShoppingList(int shoppingListID) {
        ArrayList<Product> products = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS +" WHERE " + KEY_SHOPPING_LIST_ID + " LIKE " + shoppingListID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Product product = new Product(
                        c.getInt(c.getColumnIndex(KEY_PRODUCT_ID)),
                        c.getString(c.getColumnIndex(KEY_PRODUCT_NAME)),
                        c.getDouble(c.getColumnIndex(KEY_PRODUCT_QUANTITY)),
                        c.getString(c.getColumnIndex(KEY_PRODUCT_UNIT)),
                        c.getInt(c.getColumnIndex(KEY_SHOPPING_LIST_ID)));
                products.add(product);
            } while (c.moveToNext());
        }
        return products;
    }
    public void deleteAllProductsForShoppingList(int shoppingListID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_SHOPPING_LIST_ID + " = ?",new String[]{String.valueOf(shoppingListID)});
    }
    public void deleteProduct (int productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_PRODUCT_ID + " = ?",new String[]{String.valueOf(productID)});
    }
}
