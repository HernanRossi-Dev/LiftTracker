package space.nandoh.lifttracker;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeightSelectorFragment extends Fragment {

    private static final String[] weight_types = {"Barbell", "Dumbbell", "BodyWeight", "Cable", "Machine", "Kettlebell"};
    private String weight_type;
    private String lift_choice;
    private String date;
    private ArrayAdapter<String> adapter;

    public WeightSelectorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lift_choice = getArguments().getString("lift_selected");
        date = getArguments().getString("date");
        if (savedInstanceState != null) {
            weight_type = savedInstanceState.getString("weight_type");
        }
        adapter = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                weight_types);

        return inflater.inflate(R.layout.fragment_weight_selector, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final View view = getView();
        if (view != null) {
            final ListView listView = (ListView) getView().findViewById(R.id.listview2);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parentView, View childView,
                                        int position, long id) {
                    // Start a new intent to create the rep/set/weight activity
                    weight_type = weight_types[position].toString();
                    Intent intent = new Intent(getActivity(), SetRepInput.class);
                    intent.putExtra("weight_type", weight_type);
                    intent.putExtra("lift_choice", lift_choice);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
            });
            listView.setAdapter(adapter);
        }
    }
}
