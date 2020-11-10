package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class SubwayClicked extends AppCompatActivity {
    private String station;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemclicked);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("지하철 안내");

        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        TextView name = (TextView) findViewById(R.id.clickedStation);
        TextView subName = (TextView) findViewById(R.id.clickedSubStation);

        name.setText(""+intent.getStringExtra("name"));
        subName.setText(""+intent.getStringExtra("subName"));

    }

}
