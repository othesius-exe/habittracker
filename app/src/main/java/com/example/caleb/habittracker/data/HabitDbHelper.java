package com.example.caleb.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.caleb.habittracker.data.HabitContract.HabitEntry;

/**
 * Subclass of SQLiteOpenHelper
 * Overrides onCreate and onUpgrade methods
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    // Log tag
    private static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    // Name of the database
    private static final String DATABASE_NAME = "habit.db";

    // Database Version
    // Incremented when the table is upgraded
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table with the following columns and attributes
        String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.DAY_OF_WEEK + " INTEGER NOT NULL, "
                + HabitEntry.TYPE_OF_EXERCISE + " INTEGER NOT NULL, "
                + HabitEntry.TIME_IN_MINUTES + " INTEGER NOT NULL DEFAULT 0);";
        Log.v(LOG_TAG, SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
