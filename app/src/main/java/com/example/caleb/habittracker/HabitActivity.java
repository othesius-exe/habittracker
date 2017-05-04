package com.example.caleb.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.caleb.habittracker.data.HabitContract.HabitEntry;
import com.example.caleb.habittracker.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    // Instantiate a HabitDbHelper to use throughout the activity
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // Setup for FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editorIntent = new Intent(HabitActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }
        });

        // Create a new instance of HabitDbHelper
        mDbHelper = new HabitDbHelper(this);

        // Show Database info on startup
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();;
    }

    /**
     * Helper method to display information in the onscreen TextView
     * about the state of the pets database
     */
    private void displayDatabaseInfo() {
        // Create/open a database to read from
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Choose columns to display when reading database
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.DAY_OF_WEEK,
                HabitEntry.TYPE_OF_EXERCISE,
                HabitEntry.TIME_IN_MINUTES
        };

        // Cast the TextView where data will be displayed
        TextView habitView = (TextView) findViewById(R.id.habit_display_view);

        // Create a cursor object
        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);

        try {
            // Set the header text here
            habitView.setText("There are " + cursor.getCount() + " logged sessions \n\n");

            // Append the column headers to the display
            habitView.append(HabitEntry._ID + " - " +
                        HabitEntry.DAY_OF_WEEK + " - " +
                        HabitEntry.TYPE_OF_EXERCISE + " - " +
                        HabitEntry.TIME_IN_MINUTES + " - " + "\n");

            // Get the row index
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int dayColumnIndex = cursor.getColumnIndex(HabitEntry.DAY_OF_WEEK);
            int exerciseColumnIndex = cursor.getColumnIndex(HabitEntry.TYPE_OF_EXERCISE);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.TIME_IN_MINUTES);

            // Iterate through the curosr
            while (cursor.moveToNext()) {
                // Extract the appropriate data
                int currentID = cursor.getInt(idColumnIndex);
                int currentDay = cursor.getInt(dayColumnIndex);
                int currentExercise = cursor.getInt(exerciseColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);

                // Display the info in the TextView
                habitView.append("\n" + currentID + " - " +
                        currentDay + " - " +
                        currentExercise + " - " +
                        currentTime);
            }
        } finally {
            // Always close the cursor
            cursor.close();
        }
    }

    private void insertHabit() {
        // Open the database to write to
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Write the data to be inserted to the table
        ContentValues values = new ContentValues();
        values.put(HabitEntry.DAY_OF_WEEK, "Monday");
        values.put(HabitEntry.TYPE_OF_EXERCISE, "Body Weight");
        values.put(HabitEntry.TIME_IN_MINUTES, 45);

        // Insert the data into the table
        long newHabitId = db.insert(HabitEntry.TABLE_NAME, null, values);

        Log.v("HabitActivity", "New Row ID " + newHabitId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_habit.xml file.
        // Adds the menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked a menu item
        switch (item.getItemId()) {
            // Set action for "Insert Dummy Data" option
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;
            // Respond to a click on delete
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
