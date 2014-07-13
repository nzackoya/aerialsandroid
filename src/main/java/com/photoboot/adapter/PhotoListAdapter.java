package com.photoboot.adapter;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.photoboot.action.PhotoListAction;
import com.photoboot.app.R;
import com.photoboot.domain.Photo;
import com.photoboot.fragments.PhotoFragment;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Нерсес on 05.05.2014.
 */
public class PhotoListAdapter extends ArrayAdapter<Photo> {

    Context context;
    ArrayList<Photo> selectedItems;
    ActionMode actionMode;

    public PhotoListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        selectedItems = new ArrayList<Photo>();
    }

    public ArrayList<Photo> getSelectedItems() {
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
        convertView = mInflater.inflate(R.layout.photo_list_item, null);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(getItem(position).getName());
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (getActionMode() == null) {
                    setActionMode(((ActionBarActivity) context).startActionMode(new PhotoListAction(PhotoListAdapter.this)));
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
                    Bundle photo = new Bundle();
                    photo.putSerializable("photo", getItem(position));
                    Fragment fragment = new PhotoFragment(PhotoListAdapter.this);
                    fragment.setArguments(photo);
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
