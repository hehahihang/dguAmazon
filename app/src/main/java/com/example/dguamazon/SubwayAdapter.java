package com.example.dguamazon;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SubwayAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<SubwayItem> data;
    private int layout;

    public SubwayAdapter(Context context, int layout, ArrayList<SubwayItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }
        SubwayItem subwayItem = data.get(position);

        TextView stationName = (TextView) convertView.findViewById(R.id.stationName);
        stationName.setText(subwayItem.getName());

        TextView subStation = (TextView) convertView.findViewById(R.id.subStation);
        subStation.setText(subwayItem.getSubName());

//        Button btn_from = (Button) convertView.findViewById(R.id.btn_from);
//        Button btn_to = (Button) convertView.findViewById(R.id.btn_to);

//        final String text = subwayItem.getName();
//
//        btn_from.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btn_to.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        return convertView;
    }
}
