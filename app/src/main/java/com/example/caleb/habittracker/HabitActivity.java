package com.example.caleb.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.caleb.habittracker.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {

    // Instantiate a HabitDbHelper to use throughout the activity
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
    }
}
