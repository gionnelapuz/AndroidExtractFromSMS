package com.example.gin.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class customListview extends BaseAdapter {

    private Context context;

    private ArrayList<String> message;


    public customListview(Context context, ArrayList<String> message){
        this.context = context;
        this.message = message;
           }

    @Override
    public int getCount() {
        return message.size();
    }

    @Override
    public Object getItem(int position) {
        return message.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View listView;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = inflater.inflate(R.layout.activity_item, parent, false);

        TextView mes = (TextView) listView.findViewById(R.id.txtMessage);

        mes.setText(message.get(position));

        return listView;
    }
}
