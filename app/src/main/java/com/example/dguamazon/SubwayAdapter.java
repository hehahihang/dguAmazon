package com.example.dguamazon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class SubwayAdapter extends BaseAdapter  {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SubwayItem> data;
    private int layout;//생성자로부터 전달된 아이디
    public SubwayAdapter(Context context, int layout, ArrayList<SubwayItem> data) {
        this.context = context;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
        }

        //DataSet에서 position에 해당하는 데이터를 참조하여 stationName을 설정한다.
        final SubwayItem subwayItem = data.get(position);
        final TextView stationName = (TextView) convertView.findViewById(R.id.stationName);
        stationName.setText(subwayItem.getName());

        return convertView;
    }
}
