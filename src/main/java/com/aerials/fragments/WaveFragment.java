package com.aerials.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import com.aerials.action.AerialsAction;
import com.aerials.adapter.AerialsListAdapter;
import com.aerials.app.R;
import com.aerials.domain.Wave;

public class WaveFragment extends Fragment{

    private EditText nameValue;
    private TextView name;
    private ActionMode actionMode;
    private View view;
    private Wave wave;
    private AerialsListAdapter aerialsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.aerials_frame, null);
        name = (TextView)view.findViewById(R.id.name);
//        nameValue = (EditText)view.findViewById(R.id.namevalue);
        if(getArguments() == null) {
            wave = new Wave();
            actionMode = getActivity().startActionMode(new AerialsAction(view, wave, aerialsListAdapter));
        } else {
            wave = (Wave)getArguments().getSerializable("photo");
            name.setText(wave.getTitle());
            nameValue.setText(wave.getTitle());
        }
        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem edit = menu.findItem(R.id.edit);
        edit.setVisible(true);
        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (actionMode == null) {
                    actionMode = getActivity().startActionMode(new AerialsAction(view, wave, aerialsListAdapter));
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
//
//    @Override
//    public void onDetach() {
//        edit.setVisible(true);
//        super.onDetach();
//    }
}
