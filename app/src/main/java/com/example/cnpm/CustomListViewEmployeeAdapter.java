package com.example.cnpm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

public class CustomListViewEmployeeAdapter extends ArrayAdapter<CustomListViewEmployee> {
    private Context mContext;
    int mResource;

    public CustomListViewEmployeeAdapter(@NonNull Context context, int resource, @NonNull List<CustomListViewEmployee> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String work_email = getItem(position).getWork_email();

        //CustomListViewPayroll temp = new CustomListViewPayroll(name, money);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvWorkEmail= convertView.findViewById(R.id.tvEmail);

        tvName.setText(name);
        tvWorkEmail.setText(work_email);

        return convertView;
    }

}
