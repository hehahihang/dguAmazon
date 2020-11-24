package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class SubwayClicked extends AppCompatActivity {
    TextView weather;
    TextView temperature;

    private long now = System.currentTimeMillis();

    Date date = new Date(now);
    SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String formatDate = formatNow.format(date);
    TextView dateNow;
    private Resources res = new Resources();
    TabLayout tabs;

    Fragment1 fragment1;
    Fragment2 fragment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemclicked);

        ListView listView = (ListView) findViewById(R.id.subwayListView);
        //전단계(출발/도착지 고르기)로 돌아가기

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("지하철 안내");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(formatDate);

        TextView name = (TextView) findViewById(R.id.clickedStation);
        name.setText(""+intent.getStringExtra("fromName"));

        TextView name2 = (TextView) findViewById(R.id.clickedStation2);
        name2.setText(""+intent.getStringExtra("toName"));


//        fragment 실행을 위한 부분
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();


//      fragment로 데이터 전달 --> intent로 출발/도착역 받게 되면 도착역도 fragment로 넘기자
        Bundle bundle = new Bundle();
        bundle.putString("from", name.getText().toString());
        bundle.putString("to", name2.getText().toString());
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

        weather = (TextView) findViewById(R.id.weather1);
        temperature = (TextView) findViewById(R.id.temperature);


        //날씨 크롤링을 위해 새로운 스래드 실행
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    String weatherURL = "https://weather.naver.com/today/09140139";//중구 날씨
                    Document doc = Jsoup.connect(weatherURL).get();
                    Elements elements = doc.select(".weather_area .summary  .weather");
                    Elements elements1 = doc.select(".weather_area .current");
                    String [] tmp = elements1.text().split("도");
                    String str = elements.text();

                    String weatherText = str;//날씨
                    String tempText = tmp[1].substring(0,2); //온도

                    Bundle bundle = new Bundle();
                    bundle.putString("weatherText",weatherText);
                    bundle.putString("tempText",tempText);
                    Message message = handler.obtainMessage();
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //핸들러로 뿌려준다.
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String weatherText = bundle.getString("weatherText");
            String tempText = bundle.getString("tempText");
            weather.setText(weatherText+" ");
            temperature.setText(tempText+"C");
        }
    };


}

