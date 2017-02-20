package space.nandoh.lifttracker;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

/**************************************************************************************************
 * Create by Hernan Rossi
 *      This class will be where the details of the current lift will get input and then saved to the
 *          database for documenting in the history and today sections
 *
 **************************************************************************************************/
public class SetRepInput extends Activity {
    private String  weight_type;
    private String  lift_choice;
    private double  total_weight = 0;
    private String current_set_info = "";
    private String muscle_group;

    /**********************************************************************************************
     *                              onCreate
     * @param savedInstanceState
     *
     *                  Gets called when the activity is created or recreated
     *
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rep_input);
        // Check to see if the activity is being recreated and set instance variable appropriately
        if(savedInstanceState != null) {
            total_weight = savedInstanceState.getDouble("total_weight");
            current_set_info = savedInstanceState.getString("current_set_info");
        }
        Intent intent = getIntent();
        weight_type = intent.getStringExtra("weight_type");
        lift_choice = intent.getStringExtra("lift_choice");
        muscle_group = intent.getStringExtra("muscle_group");
        String details = "Performing: " + lift_choice + " using " + weight_type;
        TextView detail_view = (TextView)findViewById(R.id.lift_details);
        detail_view.setText(details);
    }

    /**********************************************************************************************
     *                                      onStart
     *
     *              After the onCreate method has been run and the views have been created get
     *                  the view id of the current set info textView and update it with the saved
     *                  data if there is any so far
     *
     **********************************************************************************************/
    @Override
    public void onStart() {
        super.onStart();
        if(!current_set_info.equals("")) {
            TextView textView = (TextView) findViewById(R.id.current_set_info);
            textView.setText(current_set_info);
        }
    }


    /**********************************************************************************************
     *                              addSet
     * @param view
     *
     *              Launch the fragment that will take the users input for the current lift
     *
     **********************************************************************************************/
    public void addSet(View view)    {
        AddRepFragment addfrag = new AddRepFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.add_rep,  addfrag);
        //ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    /**********************************************************************************************
     *                                          saveAddSet
     * @param view
     *
     *                  Take the current input from the edit textView and add it to the total
     *                      set view then reset the edit views for the next set to be input
     *                      save the weight into the total_weight variable for saving to the database
     *
     **********************************************************************************************/
    public void saveAddSet(View view) {
        // The window to output the saved info
        TextView textView = (TextView)findViewById(R.id.current_set_info);
        // Get view to retrieve the input values
        EditText weightView = (EditText)findViewById(R.id.enter_weight);
        EditText repsView = (EditText)findViewById(R.id.enter_reps);
        String reps = repsView.getText().toString();
        String weight = weightView.getText().toString();
        try {
            double current_total = Double.parseDouble(weight) * Double.parseDouble(reps);

            total_weight = total_weight + current_total;
        }catch(NullPointerException e){

        }
        String savedSoFar = textView.getText().toString();
        String current = "    " + savedSoFar + "\n"+ weight + " lbs   x   " + reps;
        current_set_info = current;
        textView.setText(current);
        weightView.setText(null);
        repsView.setText(null);
    }

    /**********************************************************************************************
     *                                     saveExit
     * @param view
     *      saveExit method will read the user input for the current lift and save it
     *          to the SAVE_DATA table in the database
     *
     *       The table in the database has the following schema:
     *                      TABLE SAVE_DATA
     *                          _id INTEGER PRIMARY KEY AUTOINCREMENT
     *                          DATE TEXT   format: day:month:year
     *                          LIFT_NAME TEXT
     *                          WEIGHT_TYPE TEXT
     *                          REPS_WEIGHT TEXT
     *                          TOTAL_WEIGHT INTEGER
     *
     **********************************************************************************************/
    public void saveExit(View view) {
        // Save the current lift to the database and go back to main menu
        saveAddSet(view);
        MainActivity new_main = new MainActivity();
        // Get the date from the mainActivity's getDate() method
        HashMap<String, String> date_map = new_main.getDate();
        // Get the integer representation of the day month and year for database saving
        String day_num = date_map.get("day_number");
        String month = date_map.get("month");
        String year = date_map.get("year");
        String date = day_num + "\\" + month + "\\" + year;

        // Get the total user input from the textView:current_set_info
        TextView textView = (TextView)findViewById(R.id.current_set_info);
        String full_lift = textView.getText().toString();

        // Try to get a link to the database
        try{
            SQLiteOpenHelper liftDatabaseHelper = new LiftDatabaseHelper(this);
            SQLiteDatabase db = liftDatabaseHelper.getWritableDatabase();
            ContentValues lift = new ContentValues();
            lift.put("DATE", date);
            lift.put("LIFT_NAME", lift_choice);
            lift.put("MUSCLE_GROUP", muscle_group);
            lift.put("WEIGHT_TYPE", weight_type );
            lift.put("REPS_WEIGHT", full_lift);
            lift.put("TOTAL_WEIGHT", total_weight);
            db.insert("SAVE_DATA", null, lift);
            db.close();
            onBackPressed();
        }catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
           // getFragmentManager().popBackStack();
            onBackPressed();
        }
    }

    /**********************************************************************************************
     *                                      onSaveInstanceState
     *
     *              Save the instance state for the current lift info when the activity is destroyed
     *              which tracks current lift data that has been input so far by the user
     *
     **********************************************************************************************/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("current_set_info", current_set_info);
        savedInstanceState.putDouble("total_weight", total_weight);
        super.onSaveInstanceState(savedInstanceState);
    }
}
