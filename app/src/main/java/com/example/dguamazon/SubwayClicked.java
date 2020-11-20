package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class SubwayClicked extends AppCompatActivity {

    private long now = System.currentTimeMillis();

    Date date = new Date(now);
    SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String formatDate = formatNow.format(date);

    TextView dateNow;

    TabLayout tabs;

    Fragment1 fragment1;
    Fragment2 fragment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemclicked);

//        전단계(출발/도착지 고르기)로 돌아가기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("지하철 안내");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(formatDate);

        TextView name = (TextView) findViewById(R.id.clickedStation);
        name.setText(""+intent.getStringExtra("name"));

        TextView name2 = (TextView) findViewById(R.id.clickedStation2);
        name2.setText(""+intent.getStringExtra("name"));
//        fragment 실행을 위한 부분
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();


//      fragment로 데이터 전달 --> intent로 출발/도착역 받게 되면 도착역도 fragment로 넘기자
        Bundle bundle = new Bundle();
        bundle.putString("from", name.getText().toString());
        fragment1.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("와이파이 추천"));
        tabs.addTab(tabs.newTab().setText("통신사 추천"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragment1;
                else if(position == 1)
                    selected = fragment2;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();

            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}
