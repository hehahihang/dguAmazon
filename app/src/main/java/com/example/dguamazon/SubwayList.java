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

        data = new ArrayList<>();

        Resources res = new Resources();
        String [] name = res.name;
        String [] subName = res.subName;

        for(int i = 0; i <name.length; i++){
            data.add(new SubwayItem(name[i], subName[i]));
        }

        SubwayAdapter adapter = new SubwayAdapter(this, R.layout.subwayitem, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), SubwayClicked.class);
                intent.putExtra("name", data.get(position).getName());
                intent.putExtra("subName", data.get(position).getSubName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}