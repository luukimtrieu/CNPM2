package com.example.cnpm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
        String id = getItem(position).getId();
        String status = getItem(position).getStatus();
        String url = getItem(position).getUrlImage();

        //CustomListViewPayroll temp = new CustomListViewPayroll(name, money);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = convertView.findViewById(R.id.textViewName);
        TextView tvID = convertView.findViewById(R.id.textViewID);
        TextView tvStatus = convertView.findViewById(R.id.textViewStatus);
        ImageView imgAvt = convertView.findViewById(R.id.avtPr);

        tvName.setText(name);
        tvID.setText(id);
        if (url.length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(url);
            imgAvt.setImageBitmap(bitmap);
        } else {
            imgAvt.setImageResource(R.drawable.hacker);
        }
        switch (status) {
            case "Đã thanh toán":
                tvStatus.setText(status);
                tvStatus.setTextColor(Color.parseColor("#00c575"));
                break;
            case "Chưa thanh toán":
                tvStatus.setText(status);
                tvStatus.setTextColor(Color.parseColor("#d52f2f"));
                break;
            case "Paid":
                tvStatus.setText(status);
                tvStatus.setTextColor(Color.parseColor("#00c575"));
                break;
            case "Unpaid":
                tvStatus.setText(status);
                tvStatus.setTextColor(Color.parseColor("#d52f2f"));
                break;
            default:
                tvStatus.setText(status);
                break;
        }

        return convertView;
    }


}
