package com.example.cnpm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomListViewPayrollAdapter extends ArrayAdapter<CustomListViewPayroll> {
    private Context mContext;
    int mResource;

    public CustomListViewPayrollAdapter(@NonNull Context context, int resource, @NonNull List<CustomListViewPayroll> objects) {
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
        String money = getItem(position).getMoney();

        //CustomListViewPayroll temp = new CustomListViewPayroll(name, money);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = convertView.findViewById(R.id.textViewName);
        TextView tvMoney = convertView.findViewById(R.id.textViewMoney);

        tvName.setText(name);
        tvMoney.setText(money);

        return convertView;
    }
}
