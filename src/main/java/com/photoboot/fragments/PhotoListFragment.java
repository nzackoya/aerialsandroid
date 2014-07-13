package com.photoboot.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import com.photoboot.action.PhotoListAction;
import com.photoboot.adapter.PhotoListAdapter;
import com.photoboot.app.Photoboot;
import com.photoboot.app.R;
import com.photoboot.db.DatabaseHelper;
import com.photoboot.domain.Photo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nerses on 03.05.2014.
 */
public class PhotoListFragment extends Fragment {

    private ListView photoList;
    private List<Photo> photoListItems;
    private PhotoListAdapter photoListAdapter;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.photo_list_frame, null);
        if (v != null) {
            try {
                photoListItems = DatabaseHelper.getHelper().getDao(Photo.class).queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            photoListAdapter = new PhotoListAdapter(getActivity(), R.layout.photo_list_item);
            photoListAdapter.addAll(photoListItems);
            photoList = (ListView) v.findViewById(R.id.items);
            photoList.setAdapter(photoListAdapter);
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
                Fragment fragment = new PhotoFragment(photoListAdapter);
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
