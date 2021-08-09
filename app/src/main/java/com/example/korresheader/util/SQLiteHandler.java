package com.example.korresheader.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;

//Class that will make the necessary operations to the SQLite DB on the phone
public class SQLiteHandler extends SQLiteOpenHelper {

    //Static variable to establish the DB version
    private static final int DATABASE_VERSION = 1;

    //Static variable that contains the name for the DB file
    private static final String DATABASE_NAME = "KHTest.db";

    //Static variable that contains the SQL sentence to create the table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT,"
                    + FeedEntry.COLUMN_PHONENUMBER_TITLE + " INTEGER, "
                    + FeedEntry.COLUMN_AGE_TITLE + " INTEGER)";

    //Static variable that contains the SQL sentence to delete the table
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    /**
     * Constructor
     * @param context The context needed for the creation of the SQLite DB.
     */
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Saves the given contact into the DB
     * @param contact The contact to save
     */
    public void addContact(Contact contact) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_TITLE, contact.getName());
            values.put(FeedEntry.COLUMN_PHONENUMBER_TITLE, contact.getPhoneNumber());
            values.put(FeedEntry.COLUMN_AGE_TITLE, contact.getAge());
            db.insert(FeedEntry.TABLE_NAME, null, values);
            Log.i("Information", "Data added correctly to DB");
            db.close();
        } catch (Exception e) {
            Log.e("Error", "Error while adding data to DB");
        }
    }

    /**
     * Checks if a contact with the given name exist
     * @param name The name of the contact to check
     * @return True if exist, false if not
     */
    private boolean getContact(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {FeedEntry.COLUMN_NAME_TITLE};
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = " + name;
        Cursor cursor = db.query(FeedEntry.TABLE_NAME,
                projection, selection, null,
                null, null, null);
        while (cursor.moveToNext()) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    /**
     * Deletes the contact with the given name
     * @param name The name of the contact to delete
     */
    public void deleteContact(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define 'where' part of query.
        db.execSQL("DELETE FROM " + FeedEntry.TABLE_NAME
                + " WHERE " + FeedEntry.COLUMN_NAME_TITLE + " LIKE '" + name + "%';");
        db.close();
    }

    /**
     * This method returns all the contacts saved in the DB.
     * @return A HasMap with the key (the contact name) and the Contact itself of all contacts
     * saved in the DB.
     */
    public HashMap<String, Contact> getAllContacts() {
        HashMap<String, Contact> contactHashMap = new HashMap<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + FeedEntry.TABLE_NAME, null);
            while (cursor.moveToNext()) {
                contactHashMap.put(cursor.getString(1),
                        new Contact(cursor.getString(1),
                                cursor.getInt(2), cursor.getInt(3)));
            }
            Log.i("Information", "Data readed correctly");
            db.close();
        } catch (Exception e) {
            Log.e("Error", "Error while reading data from DB");
        }
        return contactHashMap;
    }

    //This class contains the name of the basic columns for the DB
    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "Contacts";
        public static final String COLUMN_NAME_TITLE = "Names";
        public static final String COLUMN_PHONENUMBER_TITLE = "Phone_Number";
        public static final String COLUMN_AGE_TITLE = "Age";

    }
}

