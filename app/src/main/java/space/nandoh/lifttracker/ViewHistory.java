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

    private ArrayAdapter<String> adapter;
    private ArrayList<String> day_array = new ArrayList<>();
    private String selected_day; // The day that has been selected by the user to see complete breakdown of

    public ViewHistory(){
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        if(savedInstanceState!=null){
        }
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
            Cursor cursor_days = db.query(tableName,
                    tableColumns,
                    whereClause,
                    whereArgs,
                    groupBy,
                    having,
                    orderBy);
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
            db.close();
            cursor_days.close();
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


    @Override
    public void onStart() {
        super.onStart();
        final String[] days = new String[day_array.size()];
        day_array.toArray(days);
        ListView listView = (ListView)findViewById(R.id.listview_history);
        TextView textView = (TextView)findViewById(R.id.history_title);
        String titleText = (String)textView.getText();
        HashMap<String, String> date = MainActivity.getDate();
        titleText = titleText + "\nToday is " + date.get("day_word") + " the " + date.get("day_number");
        textView.setText(titleText);
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
