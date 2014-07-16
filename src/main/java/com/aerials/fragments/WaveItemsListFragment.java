package com.aerials.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.aerials.action.AerialsAction;
import com.aerials.adapter.AerialsListAdapter;
import com.aerials.app.R;
import com.aerials.db.DatabaseHelper;
import com.aerials.domain.Wave;
import com.aerials.network.AerialsRequestListener;
import com.aerials.network.Request;

import java.sql.SQLException;
import java.util.List;

public class WaveItemsListFragment extends Fragment {

    private ListView waveList;
    private List<Wave> waveListItems;
    private AerialsListAdapter aerialsListAdapter;
    private View v;
    private Wave wave;
    private TextView name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        aerialsListAdapter = new AerialsListAdapter(getActivity(), R.layout.aerials_list_item);
        v = inflater.inflate(R.layout.aerials_list_frame, null);
        if (v != null) {
            if(getArguments() == null) {
//                wave = new Wave();
//                actionMode = getActivity().startActionMode(new AerialsAction(view, wave, aerialsListAdapter));
            } else {

                wave = (Wave) getArguments().getSerializable("wave");
                Request.getRSS(wave, new AerialsRequestListener<Wave>(getActivity()) {
                    @Override
                    public void gotResponse(Wave responseBean) {
                        aerialsListAdapter.addAll(responseBean);
                    }
                });
            }
            waveList = (ListView) v.findViewById(R.id.items);
            waveList.setAdapter(aerialsListAdapter);
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem newItem = menu.findItem(R.id.new_item);
        newItem.setVisible(true);
        newItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Fragment fragment = new WaveFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.content_frame, fragment).addToBackStack(null)
                        .commit();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public String toString(){
        return ((Object)this).getClass().getSimpleName();
    }
}
