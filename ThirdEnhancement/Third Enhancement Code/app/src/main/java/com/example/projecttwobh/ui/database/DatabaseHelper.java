package com.example.projecttwobh.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "warehouse.db";
    private static final int DATABASE_VERSION = 2;

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

    // ** Insert Fake Data (only if empty)**
    public void insertFakeData() {
        SQLiteDatabase db = this.getWritableDatabase();

        // **Check if data already exists**
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_INVENTORY, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {  // Only insert if table is empty
            db.execSQL("INSERT INTO " + TABLE_INVENTORY + " (" +
                    COLUMN_ITEM_NAME + ", " + COLUMN_ITEM_NUMBER + ", " +
                    COLUMN_LOCATION + ", " + COLUMN_QUANTITY + ") VALUES " +
                    "('Screwdriver', 'A123', 'Aisle 1', 50), " +
                    "('Hammer', 'B456', 'Aisle 2', 60), " +
                    "('Wrench', 'C789', 'Aisle 3', 70), " +
                    "('Drill', 'D012', 'Aisle 4', 40), " +
                    "('Pliers', 'E345', 'Aisle 5', 50), " +
                    "('Level', 'F678', 'Aisle 6', 60), " +
                    "('Drill Bit Packs', 'G901', 'Aisle 7', 40), " +
                    "('Gloves', 'H234', 'Aisle 8', 50), " +
                    "('Channel Locks', 'I567', 'Aisle 9', 70), " +
                    "('Ratchet', 'J890', 'Aisle 10', 50);");
        }

        db.close();
    }

    // ** Clear Inventory (reset auto-increment)**
    public void clearInventory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_INVENTORY);  // **Clear all rows**
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='" + TABLE_INVENTORY + "'");  // **Reset auto-increment**
        db.close();
    }

    // ** Insert a single item**
    public boolean insertItem(String itemName, String itemNumber, String location, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME, itemName);
        contentValues.put(COLUMN_ITEM_NUMBER, itemNumber);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_QUANTITY, quantity);
        long result = db.insert(TABLE_INVENTORY, null, contentValues);
        db.close();
        return result != -1;
    }

    // ** Get All Inventory Items**
    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_INVENTORY, null);
    }

    // ** User Methods**
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{username, password});
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return userExists;
    }
}
