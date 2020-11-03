package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubwayList extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<SubwayItem> data = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subwaylist);
        ListView listView = (ListView) findViewById(R.id.subwayListView);

        Resources resources = new Resources();
        String [] stn = resources.station;
        data = new ArrayList<>();
        for(int i=0;i<stn.length;i++){
            SubwayItem subwayItem = new SubwayItem(stn[i]);
            data.add(subwayItem);
        }

        SubwayAdapter adapter = new SubwayAdapter(getApplicationContext(), R.layout.subwayitem, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SubwayClicked.class);
                intent.putExtra("stationName",data.get(position).getStation());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}