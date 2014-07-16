package com.aerials.action;

import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import com.aerials.adapter.AerialsListAdapter;
import com.aerials.app.R;
import com.aerials.db.DatabaseHelper;
import com.aerials.domain.Wave;

import java.sql.SQLException;

public class AerialsAction implements ActionMode.Callback {

    private Wave wave;
    private TextView name;
    private EditText nameValue;
    private AerialsListAdapter aerialsListAdapter;

    public AerialsAction(View view, Wave wave, AerialsListAdapter aerialsListAdapter) {
        this.wave = wave;
        this.aerialsListAdapter = aerialsListAdapter;
        name = (TextView)view.findViewById(R.id.name);
//        nameValue = (EditText)view.findViewById(R.id.namevalue);
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
    }

    public void edit(){
        nameValue.setVisibility(View.VISIBLE);
        name.setVisibility(View.GONE);
    }
    public void save(){

        name.setText(nameValue.getText().toString());

        wave.setTitle(nameValue.getText().toString());

        try {
            DatabaseHelper.getHelper().getDao(Wave.class).createOrUpdate(wave);
            aerialsListAdapter.add(wave);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cancel(){
        nameValue.clearComposingText();
    }
}
