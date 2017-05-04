package com.example.caleb.habittracker.data;

import android.provider.BaseColumns;

/**
 * Contracts contain constants to be used throughout the application
 * In this case, specifically column names for the database
 */

public class HabitContract {

    private HabitContract() {}

    // Define the table contents here
    public static abstract class HabitEntry implements BaseColumns {

        // Table name
        public final static String TABLE_NAME = "habits";

        // Table column names
        public final static String _ID = BaseColumns._ID;
        public final static String DAY_OF_WEEK = "day";
        public final static String TYPE_OF_EXERCISE = "exercise";
        public final static String TIME_IN_MINUTES = "time";

        // Day of the week constants
        public static final int SUNDAY = 1;
        public static final int MONDAY = 2;
        public static final int TUESDAY = 3;
        public static final int WEDNESDAY = 4;
        public static final int THURSDAY = 5;
        public static final int FRIDAY = 6;
        public static final int SATURDAY = 7;

        // Exercise type Constants
        public static final int RUN = 1;
        public static final int SWIM = 2;
        public static final int BIKE = 3;
        public static final int WEIGHTS = 4;
        public static final int BODYWEIGHT = 5;
        public static final int YOGA = 6;
    }

}
