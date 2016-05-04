package space.nandoh.lifttracker;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.HashMap;
/***************************************************************************************************
 **
 *  Created by Hernan Rossi.
 **
 **************************************************************************************************/
public class AddLiftActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private String date;
    /**********************************************************************************************
     *                                      onCreate
     * @param savedInstanceState
     *
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_lift);
        //  SetContentView(R.layout.content_add_lift_collapse); //a test of collapsing toolbar layout
        Intent intent = getIntent();
        // Retrieve the hash map from the intent
        HashMap<String, String> date_map = (HashMap<String, String>)intent.getSerializableExtra("DateMap");
        date = new String(date_map.get("full_date"));
        // Get the time text view and add the date to the top of the activity
        TextView date_view = (TextView)findViewById(R.id.add_lift_time);
        date_view.setText(date);

        // Set up the upper body spinner
        Spinner spinner = (Spinner) findViewById(R.id.upper_body_spinner);
        // Implement an adapter for the spinner with the string-array in strings.xml
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this,
                R.array.upper_body, R.layout.spinner_item_addlift);
        // Specify the layout to use when the list appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_addlift);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
        if(mg.equals("Upper Body")) {
            // Do not launch the Fragment
        }else {
            // Set the current lifts muscle group to the selected item
            String muscleGroup = mg.toUpperCase();
            LiftNameListFragment listFrag = new LiftNameListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("date", date);
            bundle.putString("table_name", muscleGroup);
            bundle.putString("muscle_group", mg);
            listFrag.setArguments(bundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, listFrag);
            // I didn't add it to the back stack so that multiple presses wouldn't require multiple
            // back presses will find a better fix later
            // ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    /**********************************************************************************************
     *                                      onNothingSelected
     * @param parent
     *
     **********************************************************************************************/
    public void onNothingSelected(AdapterView parent) {
        // Another interface callback
    }

    /**********************************************************************************************
     *                                      lowerBodyList
     * @param view
     *
     *              launch the fragment that displays the list of lower body workouts form
     *                  the table LOWER in the database
     *
     **********************************************************************************************/
    public void lowerBodyList(View view) {
        // Create the LiftNameList fragment then launch it
        LiftNameListFragment listFrag = new LiftNameListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        bundle.putString("table_name", "LOWER");
        bundle.putString("muscle_group", "Lower Body");
        listFrag.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, listFrag);
        // I didn't add it to the back stack so that multiple presses wouldn't require multiple
        // back presses will find a better fix later
        // ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    /**********************************************************************************************
     *                                      CoreList
     * @param view
     *
     *              launch the fragment that displays the list of core body workouts form
     *                  the table CORE in the database
     *
     **********************************************************************************************/
    public void coreList(View view) {
        // Create the LiftNameList fragment then launch it
        LiftNameListFragment listFrag = new LiftNameListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        bundle.putString("table_name", "CORE");
        bundle.putString("muscle_group", "Core");
        listFrag.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, listFrag);
        // I didn't add it to the back stack so that multiple presses wouldn't require multiple
        // back presses will find a better fix later
        // ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
