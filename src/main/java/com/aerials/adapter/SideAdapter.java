package com.aerials.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.aerials.app.R;

public class SideAdapter extends ArrayAdapter<Class>{

    Context context;

    public SideAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.side_list_item, null);
        TextView name = (TextView) convertView.findViewById(R.id.text);
        name.setText(getItem(position).getSimpleName());
        return convertView;
    }
}
