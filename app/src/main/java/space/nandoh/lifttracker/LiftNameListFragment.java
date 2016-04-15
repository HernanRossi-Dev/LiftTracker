package space.nandoh.lifttracker;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**************************************************************************************************
 * Created by Hernan Rossi
 * A simple {@link android.app.Fragment} subclass.
 *
 **************************************************************************************************/
public class LiftNameListFragment extends Fragment {

    private ArrayAdapter<String> adapter;
    private String lift;
    private String date;
    private String table_name;
    private String[] names;
    // This variable will check on restart/resume whether a lift has been chosen and auto launch the
    // weight selector fragment
    private boolean lift_chosen;

    public LiftNameListFragment() {
        // Required empty public constructor
    }

    /**********************************************************************************************
     *                                      onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     *
     *              Gets called when the fragment is first created
     *
     **********************************************************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState != null) {
            lift_chosen = savedInstanceState.getBoolean("lift_chosen");
            lift = savedInstanceState.getString("lift");
            Bundle bundle = new Bundle();
            bundle.putString("lift_selected", lift);
            bundle.putString("date", date);
            WeightSelectorFragment weightSelectorFragment = new WeightSelectorFragment();
            weightSelectorFragment.setArguments(bundle);
            // Start a fragment transaction
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container2, weightSelectorFragment);
            // Don't add the fragment to the back stack : ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        date = getArguments().getString("date");
        table_name = getArguments().getString("table_name");
        // Access the database and get the names form the CORE table
        try{
            SQLiteOpenHelper liftDatabaseHelper = new LiftDatabaseHelper(getActivity());
            SQLiteDatabase db = liftDatabaseHelper.getReadableDatabase();
            // How many records are in the table?
            Cursor cursor_count = db.query(table_name,
                    new String[]{"COUNT(_id) AS count"},
                    null, null, null, null, null);
            // Populate the names array for the list view to display
            if(cursor_count.moveToFirst()) {
                int count = cursor_count.getInt(0);
                names = new String[count];
                // Close the database and cursor
                cursor_count.close();
                db.close();
                SQLiteDatabase db1 = liftDatabaseHelper.getReadableDatabase();
                // Get the names of all the records
                Cursor cursor = db1.query(table_name,
                        new String[]{"NAME"},
                        null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    names[0] = cursor.getString(0);
                    for (int i = 1; i < count; i++) {
                        if (cursor.moveToNext()) {
                            names[i] = cursor.getString(0);
                        }
                    }
                }
                db1.close();
                cursor.close();
            }
        }catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        adapter = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_activated_1,
                names);
        return inflater.inflate(R.layout.fragment_lift_name_list, container, false);
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
        final View view = getView();
        if (view != null) {
            final ListView listView = (ListView)getView().findViewById(R.id.listview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parentView, View childView,
                                        int position, long id) {
                    lift = names[position].toString();
                    // Send the lift choice to the weight selector fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("lift_selected", lift);
                    bundle.putString("date", date);
                    lift_chosen = true;
                    WeightSelectorFragment weightSelectorFragment = new WeightSelectorFragment();
                    weightSelectorFragment.setArguments(bundle);
                    // Start a fragment transaction
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container2, weightSelectorFragment);
                    // Don't add the fragment to the back stack : ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            });
            // Set the adapter to the ListView
            listView.setAdapter(adapter);
        }
    }

    /**********************************************************************************************
     *                                      onSaveInstanceState
     *
     *              Save the instance variable lift_chosen when the activity is destroyed which
     *              tracks whether or not a lift has been chosen already and automatically launch
     *              the weight type selector fragment list.
     *
     **********************************************************************************************/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("lift_chosen", lift_chosen);
        savedInstanceState.putString("lift", lift);
        super.onSaveInstanceState(savedInstanceState);
    }
}
