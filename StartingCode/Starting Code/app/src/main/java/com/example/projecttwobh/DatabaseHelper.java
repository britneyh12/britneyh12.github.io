package com.example.projecttwobh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "warehouse.db";
    private static final int DATABASE_VERSION = 1;

    // Inventory Table
    public static final String TABLE_INVENTORY = "inventory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_NUMBER = "item_number";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_QUANTITY = "quantity";

    private static final String TABLE_INVENTORY_CREATE =
            "CREATE TABLE " + TABLE_INVENTORY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ITEM_NAME + " TEXT, " +
                    COLUMN_ITEM_NUMBER + " TEXT, " +
                    COLUMN_LOCATION + " TEXT, " +
                    COLUMN_QUANTITY + " INTEGER" +
                    ");";

    // User Table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_USERS_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                    COLUMN_PASSWORD + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_INVENTORY_CREATE);
        db.execSQL(TABLE_USERS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Inventory Methods

    // Method to insert an item into the inventory table
    public boolean insertItem(String itemName, String itemNumber, String location, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME, itemName);
        contentValues.put(COLUMN_ITEM_NUMBER, itemNumber);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_QUANTITY, quantity);
        long result = db.insert(TABLE_INVENTORY, null, contentValues);
        return result != -1; // Return true if insert was successful
    }

    // Method to get all items from the inventory table
    public Cursor getAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_INVENTORY, null);
    }

    // User Methods

    // Method to add a user
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1; // Return true if insert was successful
    }

    // Method to check if a user exists
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{username, password});
        return cursor.getCount() > 0; // Return true if user exists
    }
}
