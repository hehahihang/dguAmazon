package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        TextView fromText = (TextView) findViewById(R.id.fromName);
        TextView toText = (TextView) findViewById(R.id.toName);
        String fromName = intent.getStringExtra("fromName");
        String toName = intent.getStringExtra("toName");
        fromText.setText("출발지 : "+fromName);
        toText.setText("도착지 : "+toName);
    }
}