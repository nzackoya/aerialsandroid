package com.photoboot.action;

import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import com.photoboot.adapter.PhotoListAdapter;
import com.photoboot.app.R;
import com.photoboot.db.DatabaseHelper;
import com.photoboot.domain.Photo;

import java.sql.SQLException;

/**
 * Created by Нерсес on 12.05.2014.
 */
public class PhotoAction implements ActionMode.Callback {

    private Photo photo;
    private TextView name;
    private EditText nameValue;
    private PhotoListAdapter photoListAdapter;

    public PhotoAction(View view, Photo photo, PhotoListAdapter photoListAdapter) {
        this.photo = photo;
        this.photoListAdapter = photoListAdapter;
        name = (TextView)view.findViewById(R.id.name);
        nameValue = (EditText)view.findViewById(R.id.value);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.photoboot, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.findItem(R.id.remove).setVisible(true);
        edit();
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove:
                cancel();
                mode = null;
                mode.finish();
                return false;
            default:
                save();
                mode.finish();
                return true;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        nameValue.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        if(mode!=null) {
            save();
        }
        mode = null;
    }

    public void edit(){
        nameValue.setVisibility(View.VISIBLE);
        name.setVisibility(View.GONE);
    }
    public void save(){

        name.setText(nameValue.getText().toString());

        photo.setName(nameValue.getText().toString());

        try {
            DatabaseHelper.getHelper().getDao(Photo.class).createOrUpdate(photo);
            if(photoListAdapter.photo)
            photoListAdapter.add(photo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cancel(){
        nameValue.clearComposingText();
    }
}
