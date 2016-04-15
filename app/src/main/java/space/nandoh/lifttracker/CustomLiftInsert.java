package space.nandoh.lifttracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/***************************************************************************************************
 **
 *  Created by Hernan Rossi
 **
 **************************************************************************************************/
public class CustomLiftInsert extends Activity implements AdapterView.OnItemSelectedListener {
    // Variables for save state
    private String muscleGroup = "";
    private String newLift = "";
    private String newliftDescription = "";

    /**********************************************************************************************
     *                                      onCreate
     * @param savedInstanceState
     *
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_lift_insert);
        // Populate the spinner with muscle group options
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        // Specify the interface implementation
        spinner.setOnItemSelectedListener(this);
        // Create array adapter with the string array in the strings resource file
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this,
                R.array.muscle_groups, R.layout.spinner_item);
        // Specify the layout to use when the list appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    /**********************************************************************************************
     *                                   onItemSelected
     * @param parent
     * @param view
     * @param pos
     * @param id
     *          The method that will respond to the clicking of an item in the spinner menu
     *
     **********************************************************************************************/
    public void onItemSelected(AdapterView parent, View view, int pos, long id) {
        // An item was selected. Retrieve the selected item using the
        // parent.getItemAtPosition(pos) method
        String mg = (String) parent.getItemAtPosition(pos);
        // Set the current lifts muscle group to the selected item
        muscleGroup = mg;
    }

    /**********************************************************************************************
     *                                   saveNewLift
     * @param view
     *           Method to save the new custom lift to the appropriate table in the database
     *
     **********************************************************************************************/
    public void saveNewLift(View view) {
        // Get the name and description of the new lift
        EditText editText = (EditText) findViewById(R.id.new_lift);
        newLift = editText.getText().toString();
        EditText editTextDescription = (EditText) findViewById(R.id.new_lift_description);
        newliftDescription = editTextDescription.getText().toString();
        // Try to insert the new list into the database
        try{
            SQLiteOpenHelper liftDatabaseHelper = new LiftDatabaseHelper(this);
            SQLiteDatabase db = liftDatabaseHelper.getWritableDatabase();
            ContentValues lift = new ContentValues();
            lift.put("NAME", newLift);
            lift.put("DESCRIPTION", newliftDescription);
            lift.put("SEARCH_LINK", "");
            lift.put("IMAGE_RESOURCE_ID", "");
            db.insert(muscleGroup.toUpperCase(), null, lift);
            db.close();
            onBackPressed();
        }catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**********************************************************************************************
     *
     * @param parent
     *
     **********************************************************************************************/
    public void onNothingSelected(AdapterView parent) {
        // Another interface callback
    }
}
