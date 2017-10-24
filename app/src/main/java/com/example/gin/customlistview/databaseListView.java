package com.example.gin.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gin on 8/16/2017.
 */

public class databaseListView extends BaseAdapter {

    private Context context;

    private ArrayList<String> databaseNumber;
    private ArrayList<String> databaseUsername;
    private ArrayList<String> databaseAccountNumber;


    public databaseListView(Context context, ArrayList<String> databaseNumber, ArrayList<String> databaseUsername, ArrayList<String> databaseAccountNumber){
        this.context = context;
        this.databaseNumber = databaseNumber;
        this.databaseUsername = databaseUsername;
        this.databaseAccountNumber = databaseAccountNumber;
    }

    @Override
    public int getCount() {
        return databaseNumber.size();
    }

    @Override
    public Object getItem(int position) {
        return databaseNumber.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View listView;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = inflater.inflate(R.layout.database_item, parent, false);

        TextView num = (TextView) listView.findViewById(R.id.txtDatabaseNumber);
        TextView username = (TextView) listView.findViewById(R.id.txtUsername);
        TextView accountNumber = (TextView) listView.findViewById(R.id.txtAccountNumber);

        num.setText(databaseNumber.get(position));
        username.setText(databaseUsername.get(position));
        accountNumber.setText(databaseAccountNumber.get(position));

        return listView;
    }
}
