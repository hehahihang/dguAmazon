package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class SubwayClicked extends AppCompatActivity {

    private ArrayList<SubwayItem> data = null;
    private long now = System.currentTimeMillis();

    Date date = new Date(now);
    SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String formatDate = formatNow.format(date);

    TextView dateNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemclicked);

        ListView listView = (ListView) findViewById(R.id.subwayListView);


//        전단계(출발/도착지 고르기)로 돌아가기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("지하철 안내");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(formatDate);
        TextView name = (TextView) findViewById(R.id.clickedStation);
        TextView subName = (TextView) findViewById(R.id.clickedSubStation);

        name.setText(""+intent.getStringExtra("name"));
        subName.setText(""+intent.getStringExtra("subName"));

    }

}
