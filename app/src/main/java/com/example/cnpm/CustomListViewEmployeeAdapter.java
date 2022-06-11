package com.example.cnpm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomListViewEmployeeAdapter extends ArrayAdapter<CustomListViewEmployee> implements Filterable {
    private Context mContext;
    int mResource;
    private List<CustomListViewEmployee> itemsModelsl;
    private List<CustomListViewEmployee> itemsModelListFiltered;


    public CustomListViewEmployeeAdapter(@NonNull Context context, int resource, @NonNull List<CustomListViewEmployee> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        itemsModelsl = objects;
        itemsModelListFiltered = objects;
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


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = itemsModelsl.size();
                    filterResults.values = itemsModelsl;

                }else{
                    List<CustomListViewEmployee> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CustomListViewEmployee itemsModel:itemsModelsl){
                        if(itemsModel.getName().contains(searchStr) /*|| itemsModel.getEmail().contains(searchStr)*/){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsModelListFiltered = (List<CustomListViewEmployee>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;

    }





}
