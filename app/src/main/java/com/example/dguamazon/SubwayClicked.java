package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SubwayClicked extends AppCompatActivity {
    private String station;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemclicked);

        Intent intent = getIntent();
        TextView duration = (TextView) findViewById(R.id.duration);
        duration.setText(Resources.duration);

        TextView stationName = (TextView) findViewById(R.id.clickedStation);
        station = intent.getStringExtra("stationName");
        stationName.setText(station);


    }
}
