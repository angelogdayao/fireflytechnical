package com.test.firefly.fireflytechnical.recyclerviewdata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

import static com.test.firefly.fireflytechnical.recyclerviewdata.ItemTypeContract.ItemEntry.TABLE_NAME;


public class ItemDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ItemDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "fireflytechnical.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ItemDbHelper}.
     *
     * @param context of the app
     */
    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method that returns column names of SQL database.
     * For debugging purposes only.
     * @param db
     * @param tableName
     * @return
     */
    public String getColumnNames(SQLiteDatabase db, String tableName) {
        Cursor dbCursor = db.query(tableName, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        return Arrays.toString(columnNames);
    }

    /** This is called when the database is created for the first time. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the items table
        String SQL_CREATE_ITEMS_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
                + ItemTypeContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemTypeContract.ItemEntry.COLUMN_ITEM_TYPE_CODE + " TEXT NOT NULL, "
                + ItemTypeContract.ItemEntry.COLUMN_ITEM_TYPE_DESCRIPTION + " TEXT NOT NULL, "
                + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ITEMS_TABLE);

        // Debugging logs
        Log.v("ItemDbHelper", "SQL TABLE CREATION = " + SQL_CREATE_ITEMS_TABLE);
        Log.v("ItemDbHelper", "Column Names = " + getColumnNames(db, TABLE_NAME));
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
