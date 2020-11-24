package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    public List<Data> subwayList;
    DataAdapter mDbHelper;

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

        //이 객체에서 DB에 모두 담는다.
        mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        System.out.println("지하철역 "+subwayList.get(0).getStation());
    }

    //이 액티비티가 꺼질때 같이 onDestroy();
    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}