package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class SubwayClicked extends AppCompatActivity {
    TextView weather;
    TextView temperature;

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
        //전단계(출발/도착지 고르기)로 돌아가기
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