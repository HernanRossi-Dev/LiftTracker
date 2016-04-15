package space.nandoh.lifttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/***************************************************************************************************
 **
 *  Created by Hernan Rossi on 4/6/2016.
 **
 **************************************************************************************************/
public class LiftDatabaseHelper extends SQLiteOpenHelper {

    // Databases for the lift tables.
    private static final String DB_NAME_LIFTS = "lifts";
    private static final int DB_LIFTS_VERSION = 1;
    // _id, muscle group,, name, description, link to google search, image? if can find non copyright pictures.
    //_id, date in day/month/year, lift type, weight type, weight, reps, average wight, total weight lifted, average reps.

    /***********************************************************************************************
     *
     *   SQLiteOpenHelper default constructor
     *
     **********************************************************************************************/
    LiftDatabaseHelper(Context context) {
        super(context, DB_NAME_LIFTS, null, DB_LIFTS_VERSION);
    }

    /**********************************************************************************************
     *
     *   onCreate method is a sqlite database helper method
     *       is called when database is first created and populates it with tables and records
     *
     **********************************************************************************************/
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create all the muscle group tables
        updateMyDatabase(db, 0, DB_LIFTS_VERSION);
    }

    /**********************************************************************************************
     *
     *   onUpgrade method is a built in method of the database helper class
     *      checks if an upgrade is needed and acts appropriately according to custom actions
     *
     **********************************************************************************************/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    /**********************************************************************************************
     *
     *       Helper method to insert items into the individual muscle group tables of the database
     *
     *********************************************************************************************/
    private static void insertLift(SQLiteDatabase db, String name, String description,
                                        String searchLink, String image_id, String muscleGroup) {
        ContentValues lift = new ContentValues();
        lift.put("NAME", name);
        lift.put("DESCRIPTION", description);
        lift.put("SEARCH_LINK", searchLink);
        lift.put("IMAGE_RESOURCE_ID", image_id);
        db.insert(muscleGroup, null, lift);
    }

    /**********************************************************************************************
     *
     *       Create a new muscle group table for lifts
     *
     **********************************************************************************************/
    private void createTable(SQLiteDatabase db, String tableName) {
        db.execSQL("CREATE TABLE " + tableName + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "DESCRIPTION TEXT, "
                + "SEARCH_LINK TEXT, "
                + "IMAGE_RESOURCE_ID INTEGER);");
    }

    /**********************************************************************************************
     *
     * Helper method to either create or upgrade the database if it is needed
     *
     *********************************************************************************************/
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 1) {
            // Create the default muscle group tables
            createTable(db, "LOWER");
            createTable(db, "CORE");
            createTable(db, "SHOULDER");
            createTable(db, "CHEST");
            createTable(db, "BACK");
            createTable(db, "ARMS");
            // Begin inserting values in to the lower table of the lifts database
            insertLift(db, "Lunges", "none", "none", "none", "LOWER");
            insertLift(db, "Back Squats", "none", "none", "none", "LOWER");
            insertLift(db, "Split Squat", "none", "none", "none", "LOWER");
            insertLift(db, "Calf raises", "none", "none", "none", "LOWER");
            insertLift(db, "Step-up", "none", "none", "none", "LOWER");
            insertLift(db, "Deadlift", "none", "none", "none", "LOWER");
            insertLift(db, "Leg curl", "none", "none", "none", "LOWER");
            // Populate the core table
            insertLift(db, "Crunches", "none", "none", "none", "CORE");
            insertLift(db, "Deadlift", "none", "none", "none", "CORE");
            insertLift(db, "Russian Twist", "none", "none", "none", "CORE");
            insertLift(db, "Side bend", "none", "none", "none", "CORE");
            insertLift(db, "T-pushups", "none", "none", "none", "CORE");
            insertLift(db, "Decline crunch", "none", "none", "none", "CORE");
            insertLift(db, "V ups", "none", "none", "none", "CORE");
            insertLift(db, "Leg curl", "none", "none", "none", "CORE");
            insertLift(db, "Side plank", "none", "none", "none", "CORE");
            insertLift(db, "Plank", "none", "none", "none", "CORE");
            insertLift(db, "Swing", "none", "none", "none", "CORE");
            insertLift(db, "Woodchop", "none", "none", "none", "CORE");
            insertLift(db, "180 Landmines", "none", "none", "none", "CORE");
            // Populate the shoulder table
            insertLift(db, "Upright row", "none", "none", "none", "SHOULDER");
            insertLift(db, "Front raise", "none", "none", "none", "SHOULDER");
            insertLift(db, "Shoulder press", "none", "none", "none", "SHOULDER");
            insertLift(db, "Seated shoulder press", "none", "none", "none", "SHOULDER");
            insertLift(db, "Shoulder press (behind the neck)", "none", "none", "none", "SHOULDER");
            insertLift(db, "Arnold dumbbell press", "none", "none", "none", "SHOULDER");
            insertLift(db, "Lateral raise", "none", "none", "none", "SHOULDER");
            insertLift(db, "Deltoid raise", "none", "none", "none", "SHOULDER");
            // Populate the back table
            insertLift(db, "Deadlift", "none", "none", "none", "BACK");
            insertLift(db, "Bent-over row", "none", "none", "none", "BACK");
            insertLift(db, "Pull-ups", "none", "none", "none", "BACK");
            insertLift(db, "Standing T-bar row", "none", "none", "none", "BACK");
            insertLift(db, "Seated row", "none", "none", "none", "BACK");
            insertLift(db, "Pull down", "none", "none", "none", "BACK");
            insertLift(db, "Single arm row", "none", "none", "none", "BACK");
            // Populate the chest table
            insertLift(db, "Bench press", "none", "none", "none", "CHEST");
            insertLift(db, "Incline press", "none", "none", "none", "CHEST");
            insertLift(db, "Incline bench fly", "none", "none", "none", "CHEST");
            insertLift(db, "Seated chest press", "none", "none", "none", "CHEST");
            insertLift(db, "Dips", "none", "none", "none", "CHEST");
            insertLift(db, "Peck-deck", "none", "none", "none", "CHEST");
            // Populate the arms table
            insertLift(db, "Curl", "none", "none", "none", "ARMS");
            insertLift(db, "Incline curl", "none", "none", "none", "ARMS");
            insertLift(db, "Preacher curl", "none", "none", "none", "ARMS");
            insertLift(db, "Reverse curl", "none", "none", "none", "ARMS");
            insertLift(db, "Hammer curl", "none", "none", "none", "ARMS");
            insertLift(db, "Triceps push down", "none", "none", "none", "ARMS");
            insertLift(db, "Seated triceps press", "none", "none", "none", "ARMS");
            insertLift(db, "Lying triceps press", "none", "none", "none", "ARMS");
            insertLift(db, "Overhead triceps extension", "none", "none", "none", "ARMS");
            insertLift(db, "Dips", "none", "none", "none", "ARMS");
            // Create the save_data table
            // _id, date, lift name, weight type, reps and weight text from view, total weight lifted
            db.execSQL("CREATE TABLE SAVE_DATA ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DATE TEXT, "         //  Will be in day:month:year
                    + "LIFT_NAME TEXT, "
                    + "WEIGHT_TYPE TEXT, "
                    + "REPS_WEIGHT TEXT, "
                    + "TOTAL_WEIGHT INTEGER);");
            // A table to store the saved values for the users personal weight that day
            db.execSQL("CREATE TABLE WEIGHT_TRACK ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DATE TEXT, "
                    + "WEIGHT TEXT);");
        }
        // Possible future upgrade conditionals
    }
}