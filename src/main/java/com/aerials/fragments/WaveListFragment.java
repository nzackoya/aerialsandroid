package com.aerials.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.widget.ListView;
import com.aerials.adapter.AerialsListAdapter;
import com.aerials.app.R;
import com.aerials.db.DatabaseHelper;
import com.aerials.domain.Wave;

import java.sql.SQLException;
import java.util.List;

public class WaveListFragment extends Fragment {

    private ListView waveList;
    private List<Wave> waveListItems;
    private AerialsListAdapter aerialsListAdapter;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.aerials_list_frame, null);
        if (v != null) {
            try {
                waveListItems = DatabaseHelper.getHelper().getDao(Wave.class).queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            aerialsListAdapter = new AerialsListAdapter(getActivity(), R.layout.aerials_list_item);
            aerialsListAdapter.addAll(waveListItems);
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
                Fragment fragment = new WaveItemsListFragment();
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
