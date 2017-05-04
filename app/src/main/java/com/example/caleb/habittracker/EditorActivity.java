package com.example.caleb.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caleb.habittracker.data.HabitContract.HabitEntry;
import com.example.caleb.habittracker.data.HabitDbHelper;

/**
 * Code for the Editor page
 */

public class EditorActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    private EditText mTimeEditText;

    private Spinner mDayOfWeekSpinner;

    private Spinner mExerciseSpinner;

    private int mDayOfWeek = 0;

    private int mTypeOfExercise = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to get user input
        mDayOfWeekSpinner = (Spinner) findViewById(R.id.day_spinner);
        mExerciseSpinner = (Spinner) findViewById(R.id.exercise_spinner);
        mTimeEditText = (EditText) findViewById(R.id.time_edit_text);

        setUpDaySpinner();
        setUpExerciseSpinner();

        mDbHelper = new HabitDbHelper(this);
    }

    /**
     * Setup the dropdown spinner for days of the week
     */
    private void setUpDaySpinner() {
        // Create an adapter for the spinner
        // Use the default layout
        ArrayAdapter daySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_day_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - 1 item per line
        daySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDayOfWeekSpinner.setAdapter(daySpinnerAdapter);

        // Set the integer to the constant values
        mDayOfWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.sunday))) {
                        mDayOfWeek = HabitEntry.SUNDAY; // Sunday
                    } else if (selection.equals(getString(R.string.monday))) {
                        mDayOfWeek = HabitEntry.MONDAY; // Monday
                    } else if (selection.equals(getString(R.string.tuesday))) {
                        mDayOfWeek = HabitEntry.TUESDAY; // Tuesday
                    } else if (selection.equals(getString(R.string.wednesday))) {
                        mDayOfWeek = HabitEntry.WEDNESDAY; // Wednesday
                    } else if (selection.equals(getString(R.string.thursday))) {
                        mDayOfWeek = HabitEntry.THURSDAY; // Thursday
                    } else if (selection.equals(getString(R.string.friday))) {
                        mDayOfWeek = HabitEntry.FRIDAY; // Friday
                    } else {
                        mDayOfWeek = HabitEntry.SATURDAY; // Saturday
                    }
                }
            }

            // Must define onNothingSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDayOfWeek = 0; // Nothing selected
            }
        });
    }

    private void setUpExerciseSpinner() {
        // Create an adapter for the spinner
        // Use the default layout
        ArrayAdapter exerciseSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_exercise_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - 1 item per line
        exerciseSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter
        mExerciseSpinner.setAdapter(exerciseSpinnerAdapter);

        // Set the integer to the constant values
        mExerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.run))) {
                        mTypeOfExercise = HabitEntry.RUN; // Run
                    } else if (selection.equals(getString(R.string.swim))) {
                        mTypeOfExercise = HabitEntry.SWIM; // Swim
                    } else if (selection.equals(getString(R.string.bike))) {
                        mTypeOfExercise = HabitEntry.BIKE; // Bike
                    } else if (selection.equals(getString(R.string.weights))) {
                        mTypeOfExercise = HabitEntry.WEIGHTS; // Weights
                    } else if (selection.equals(getString(R.string.bodyweight))) {
                        mTypeOfExercise = HabitEntry.BODYWEIGHT; // Body weight
                    } else {
                        mTypeOfExercise = HabitEntry.YOGA; // Yoga
                    }
                }
            }

            // Must define onNothingSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTypeOfExercise = 0; // Nothing selected
            }
        });
    }

    // Insert a user created habit
    private void insertHabit() {
        // Get the database to write to
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Retrieve user input from Editor page
        String timeString = mTimeEditText.getText().toString().trim();

        Integer timeInt = Integer.parseInt(timeString);

        // Associate values with specific table fields
        ContentValues values = new ContentValues();
        values.put(HabitEntry.DAY_OF_WEEK, mDayOfWeek);
        values.put(HabitEntry.TYPE_OF_EXERCISE, mTypeOfExercise);
        values.put(HabitEntry.TIME_IN_MINUTES, timeInt);

        // Insert the data into the database
        // Store data in a long for logging purposes
        Long newHabitId = db.insert(HabitEntry.TABLE_NAME, null, values);

        // Log the new row
        Log.v("EditorActivity", "New Row ID " + newHabitId);

        // Create a toast that shows the new row id
        if (newHabitId == -1) {
            Toast.makeText(this, "Error saving exercise!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Exercise saved with ID: " + newHabitId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file
        // Adds menu items to the app bar
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar
        switch (item.getItemId()) {
            // Respond to the click
            case R.id.action_save:
                // Insert new habit
                insertHabit();
                // Exit the activity
                finish();
                return true;
            case R.id.action_delete:
                // do nothing for now
                return true;
            // Respond to a click on the arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (HabitActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
