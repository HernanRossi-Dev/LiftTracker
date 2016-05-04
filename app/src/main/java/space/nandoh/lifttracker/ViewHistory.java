package space.nandoh.lifttracker;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/***************************************************************************************************
 **
 *  Created by Hernan Rossi.
 **                 Activity to search through the entire database of saved lifts and list them
 *                  in chronological order on list entry for every day with a title of the most
 *                  worked muscle group of that day whether it was a morning afternoon or evening
 *                  workout the users body weight if it was given for that day and the total weight
 *                  lifted over all of the lifts done that day. Clicking on a day in the list will
 *                  launch a new activity which will show the user a more detailed breakdown of
 *                  the lifts that were performed on that day an another other statistics available
 **************************************************************************************************/
public class ViewHistory extends Activity {

    private ArrayAdapter<String> adapter; // Array adapter to add the individual days to the viewList
    private ArrayList<String> day_array = new ArrayList<>(); // Hold date for each individual day in the saved data
    private String selected_day; // The day that has been selected by the user to see complete breakdown of

    public ViewHistory(){
    }

    /**********************************************************************************************
     *                                      onCreate
     * @param savedInstanceState
     *
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        if(savedInstanceState!=null){
            // Load saved values
        }
        // Get a reference to the database and create a readable object to find all of the data
        try{
            SQLiteOpenHelper liftDatabaseHelper = new LiftDatabaseHelper(this);
            SQLiteDatabase db = liftDatabaseHelper.getReadableDatabase();
            // Get all of the individual days that are in the history table
            // .query(tableName, tableColumns, whereClause, whereArgs, groupBy, having, orderBy);
            String tableName = "SAVE_DATA";
            String[] tableColumns = new String[] {"DISTINCT DATE"};
            String whereClause = null;
            String[] whereArgs = null;
            String groupBy = null;
            String having = null;
            String orderBy = "DATE";
            // Query the database
            Cursor cursor_days = db.query(tableName,
                    tableColumns,
                    whereClause,
                    whereArgs,
                    groupBy,
                    having,
                    orderBy);
            // Process the result of the query to the database
            if(cursor_days.moveToFirst()){
                String temp_day = cursor_days.getString(0);
                String result_day = temp_day.replace("\\", " ");
                day_array.add(result_day);
                while(cursor_days.moveToNext()){
                    temp_day = cursor_days.getString(0);
                    result_day = temp_day.replace("\\", " ");
                    day_array.add(result_day);
                }
            }
            // Close database reference and cursor
            db.close();
            cursor_days.close();
            // Begin retrieving all of the days for which there is saved data
        }catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        // Setup the listView adapter to show data for days
        String[] days = new String[day_array.size()];
        day_array.toArray(days);
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_activated_1, days);
    }

    /**********************************************************************************************
     *                                      onStart
     *
     *              After the onCreate method has been run and the views have been created get
     *                  the view id of the list view and link it to the array adapter and set
     *                  a setOnItemClickListener to react to user selection that will launch the
     *                  weight WeightSelectorFragment that will ask the user what type of weight
     *                  they are using for the current lift.
     *
     **********************************************************************************************/
    @Override
    public void onStart() {
        super.onStart();
        final String[] days = new String[day_array.size()];
        day_array.toArray(days);
        ListView listView = (ListView)findViewById(R.id.listview_history);
        // Update the title to show the current day to the user for ease of reading
        TextView textView = (TextView)findViewById(R.id.history_title);
        String titleText = (String)textView.getText();
        HashMap<String, String> date = MainActivity.getDate();
        titleText = titleText + "\nToday is " + date.get("day_word") + " the " + date.get("day_number");
        textView.setText(titleText);

        // Create an onItemClickListener to the listView to get the day that the user would like to see
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parentView, View childVIew,
                                    int position, long id ) {
                // Method to run when an item in the list of days is clicks
                selected_day = days[position].toString();

            }
        });
        listView.setAdapter(adapter);
    }

}
