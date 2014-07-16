package com.aerials.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.aerials.action.AerialsListAction;
import com.aerials.app.R;
import com.aerials.domain.Wave;
import com.aerials.fragments.WaveFragment;
import com.aerials.fragments.WaveItemsListFragment;

import java.util.ArrayList;

public class AerialsListAdapter extends ArrayAdapter<Wave> {

    Context context;
    ArrayList<Wave> selectedItems;
    ActionMode actionMode;

    public AerialsListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        selectedItems = new ArrayList<Wave>();
    }

    public ArrayList<Wave> getSelectedItems() {
        return selectedItems;
    }

    public ActionMode getActionMode() {
        return actionMode;
    }

    public void setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.aerials_list_item, null);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(getItem(position).getTitle());
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (getActionMode() == null) {
                    setActionMode(((ActionBarActivity) context).startActionMode(new AerialsListAction(AerialsListAdapter.this)));
                }
                if (view.isActivated()) {
                    getSelectedItems().remove(getItem(position));
                } else {
                    getSelectedItems().add(getItem(position));
                }
                view.setActivated(!view.isActivated());
                return true;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActionMode() != null) {
                    if (view.isActivated()) {
                        getSelectedItems().remove(getItem(position));
                    } else {
                        getSelectedItems().add(getItem(position));
                    }
                    view.setActivated(!view.isActivated());
                } else {
                    Bundle wave = new Bundle();
                    wave.putSerializable("wave", getItem(position));
                    Fragment fragment = new WaveItemsListFragment();
                    fragment.setArguments(wave);
                    FragmentManager fragmentManager = ((ActionBarActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.content_frame, fragment).addToBackStack(null)
                            .commit();
                }
            }
        });
        return convertView;
    }
}
