package com.example.dguamazon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.dguamazon.R;
import com.example.dguamazon.SubwayItem;

import java.util.ArrayList;

public class SubwayList extends Activity implements View.OnClickListener {
    private ArrayList<SubwayItem> data = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subwaylist);
        ListView listView = (ListView) findViewById(R.id.subwayListView);
        String [] stn = {"신도림","문래","강남","사당","홍대","을지로4가","영등포구청","서초","방배","교대","건대입구"};
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
