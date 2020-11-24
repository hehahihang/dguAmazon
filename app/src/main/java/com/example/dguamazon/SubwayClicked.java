package com.example.dguamazon;

import android.content.Intent;
import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class SubwayClicked extends AppCompatActivity {
    DataAdapter mDbHelper; //데이터베이스 불러오기위한 DBHelper

    TextView weather;
    TextView temperature;
    TextView day;

    private long now = System.currentTimeMillis();
    private List<Data> subwayData = new ArrayList<>();


    Date date = new Date(now);
    SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String formatDate = formatNow.format(date);

    SimpleDateFormat formatHour = new SimpleDateFormat("HH");
    String Hours = formatHour.format(date);

    TextView dateNow;

    TabLayout tabs;

    Fragment1 fragment1;
    Fragment2 fragment2;

    String fromName;
    String toName;
    int fromCode;
    int toCode;


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
        fromName = intent.getStringExtra("fromName");
        name.setText("" + fromName);

        TextView name2 = (TextView) findViewById(R.id.clickedStation2);
        toName = intent.getStringExtra("toName");
        name2.setText("" + toName);

        fromCode = intent.getIntExtra("fromCode",-1);
        toCode = intent.getIntExtra("toCode",-1);

        if(fromCode!=-1 && toCode!=-1){
            System.out.println("출발지 코드 : "+ fromCode+" 도착지 코드 : "+toCode);
        }

//      fragment 실행을 위한 부분
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
                if (position == 0)
                    selected = fragment1;
                else if (position == 1)
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
        day = (TextView) findViewById(R.id.day);


        //날씨 크롤링을 위해 새로운 쓰래드 실행
        //외부 Thread를 통해 내부적으로 멈추는 것을 방지
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String weatherURL = "https://weather.naver.com/today/09140139";//중구 날씨
                    Document doc = Jsoup.connect(weatherURL).get();
                    Elements elements = doc.select(".weather_area .summary  .weather");
                    Elements elements1 = doc.select(".weather_area .current");
                    Elements elements2 = doc.select(".button .day");
                    String[] tmp2 = elements2.text().split(" ");
                    String[] tmp = elements1.text().split("도");
                    String str = elements.text();

                    String weatherText = str;//날씨
                    String tempText = tmp[1].substring(0, 2); //온도
                    String dayText = tmp2[0]; //요일


                    //어댑터를 통해 지금 context로 DB를 불러온다.
                    mDbHelper = new DataAdapter(getApplicationContext());
                    mDbHelper.createDatabase();
                    mDbHelper.open();

                    subwayData = mDbHelper.getTableData(fromName, toName, weatherText, dayText, Hours);
                    int size = subwayData.size();

                    System.out.println("출발역 개수 : " + size);
                    System.out.println(fromName + " " + toName);
                    System.out.println(Hours+"  : 시간대");


                    Bundle bundle = new Bundle();
                    bundle.putString("weatherText", weatherText);
                    bundle.putString("tempText", tempText);
                    bundle.putString("dayText", dayText);

                    Message message = handler.obtainMessage();
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //핸들러로 뿌려준다.
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String weatherText = bundle.getString("weatherText"); //현재 날씨
            String tempText = bundle.getString("tempText"); //현재 온도
            String dayText = bundle.getString("dayText"); //현재 날짜
            weather.setText(weatherText + " ");
            temperature.setText(tempText + "C");
            day.setText(" " + dayText);
        }
    };
}
//    public void readSubwayData(){
//        InputStream is =  getResources().openRawResource(R.raw.data);
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(is, Charset.forName("UTF-8"))
//        );
//        String line = "";
//
//        try{
//            while((line = reader.readLine())!=null){
//                String [] tokens = line.split(",");
//                Data data = new Data();
//                data.setStation(tokens[0]);
//                data.setDays(tokens[1]);
//                data.setWeather(tokens[2]);
//                data.setHours(Integer.parseInt(tokens[3]));
//                data.setSsid(tokens[4]);
//                data.setScore(Double.parseDouble(tokens[5]));
//                subwayData.add(data);
//                Log.d("Data Input","Just created " + data.getStation() + " " + data.getScore());
//            }
//        }
//        catch (IOException e){
//            Log.wtf("SubwayClicked Activity","Reading Data Error"+line, e);
//            e.printStackTrace();
//        }
//    }
