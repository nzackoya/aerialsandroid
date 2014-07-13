package com.photoboot.action;

import android.view.*;
import com.photoboot.adapter.PhotoListAdapter;
import com.photoboot.app.R;
import com.photoboot.db.DatabaseHelper;
import com.photoboot.domain.Photo;

import java.sql.SQLException;

/**
 * Created by Нерсес on 12.05.2014.
 */
public class PhotoListAction implements ActionMode.Callback {

    PhotoListAdapter photoListAdapter;

    public PhotoListAction(PhotoListAdapter photoListAdapter) {
        this.photoListAdapter = photoListAdapter;
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
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove:
                remove();
                mode.finish();
                photoListAdapter.setActionMode(null);
                return true;
            default:
                mode.finish();
                photoListAdapter.setActionMode(null);
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        photoListAdapter.notifyDataSetChanged();
        photoListAdapter.getSelectedItems().clear();
        photoListAdapter.setActionMode(null);
    }
    public void remove(){
        for(Photo photo : photoListAdapter.getSelectedItems()){
            photoListAdapter.remove(photo);
            try {
                DatabaseHelper.getHelper().getDao(Photo.class).delete(photo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
