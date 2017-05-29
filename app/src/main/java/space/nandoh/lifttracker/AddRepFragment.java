package space.nandoh.lifttracker;


import android.app.Fragment;
import android.os.Bundle;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AddRepFragment extends Fragment {

    public AddRepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_rep_fragement, container, false);
    }
}
