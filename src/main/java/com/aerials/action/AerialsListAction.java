package com.aerials.action;

import android.view.*;
import com.aerials.adapter.AerialsListAdapter;
import com.aerials.app.R;
import com.aerials.db.DatabaseHelper;
import com.aerials.domain.Wave;

import java.sql.SQLException;

public class AerialsListAction implements ActionMode.Callback {

    AerialsListAdapter aerialsListAdapter;

    public AerialsListAction(AerialsListAdapter aerialsListAdapter) {
        this.aerialsListAdapter = aerialsListAdapter;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.aerials, menu);
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
                aerialsListAdapter.setActionMode(null);
                return true;
            default:
                mode.finish();
                aerialsListAdapter.setActionMode(null);
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        aerialsListAdapter.notifyDataSetChanged();
        aerialsListAdapter.getSelectedItems().clear();
        aerialsListAdapter.setActionMode(null);
    }
    public void remove(){
        for(Wave wave : aerialsListAdapter.getSelectedItems()){
            aerialsListAdapter.remove(wave);
            try {
                DatabaseHelper.getHelper().getDao(Wave.class).delete(wave);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
