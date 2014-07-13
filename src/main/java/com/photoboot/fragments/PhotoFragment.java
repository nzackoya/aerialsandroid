package com.photoboot.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import com.photoboot.action.PhotoAction;
import com.photoboot.adapter.PhotoListAdapter;
import com.photoboot.app.Photoboot;
import com.photoboot.app.R;
import com.photoboot.db.DatabaseHelper;
import com.photoboot.domain.Photo;

import java.sql.SQLException;

/**
 * Created by Nerses on 03.05.2014.
 */
public class PhotoFragment extends Fragment{

    private EditText nameValue;
    private TextView name;
    private ActionMode actionMode;
    private View view;
    private Photo photo;
    private PhotoListAdapter photoListAdapter;

    public PhotoFragment(PhotoListAdapter photoListAdapter) {
        this.photoListAdapter = photoListAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.photo_frame, null);
        name = (TextView)view.findViewById(R.id.name);
        nameValue = (EditText)view.findViewById(R.id.value);
        if(getArguments() == null) {
            photo = new Photo();
            actionMode = getActivity().startActionMode(new PhotoAction(view, photo, photoListAdapter));
        } else {
            photo = (Photo)getArguments().getSerializable("photo");
            name.setText(photo.getName());
            nameValue.setText(photo.getName());
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
                    actionMode = getActivity().startActionMode(new PhotoAction(view, photo, photoListAdapter));
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
