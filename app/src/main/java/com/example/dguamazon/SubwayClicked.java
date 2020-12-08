package com.example.dguamazon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
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
    //데이터베이스 불러오기위한 DBHelper;
    DataAdapter mDbHelper;

    //현재 날씨, 기온, 날짜를 출력하는 Textview
    TextView weather;
    TextView temperature;
    TextView day;

    //DB에서 조건에 맞는 객체를 추출하여 저장할 ArrayList<Data>
    List<Data> subwayData = new ArrayList<>();

    //현재 시간(HH)을 받아오기 위해 사용한 currentTimeMills, Date 클래스, SDF
    private long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat formatHour = new SimpleDateFormat("HH");
    String Hours = formatHour.format(date);

    //프래그먼트 전환을 위한 탭
    TabLayout tabs;

    //Framgment 1,2
    Fragment1 fragment1;
    Fragment2 fragment2;

    //출발지 도착지 이름
    String fromName;
    String toName;

    //출발지 도착치 코드
    int fromCode;
    int toCode;


//    Resources res = new Resources();
//    ArrayList<String> totalStation = new ArrayList<>(Arrays.asList(res.name));
//    protected ArrayList<String> rootStation = null;

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

        TextView name = (TextView) findViewById(R.id.clickedStation);
        fromName = intent.getStringExtra("fromName");
        name.setText("" + fromName);
        name.setSelected(true);

        TextView name2 = (TextView) findViewById(R.id.clickedStation2);
        toName = intent.getStringExtra("toName");
        name2.setText("" + toName);
        name2.setSelected(true);

        fromCode = intent.getIntExtra("fromCode",-1);
        toCode = intent.getIntExtra("toCode",-1);

        if(fromCode!=-1 && toCode!=-1){
            System.out.println("출발지 코드 : "+ fromCode+" 도착지 코드 : "+toCode);
        }

//      fragment 실행을 위한 부분
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        final Bundle bundle = new Bundle();
//      fragment로 데이터 전달 --> intent로 출발/도착역 받게 되면 도착역도 fragment로 넘기자
        bundle.putString("from", name.getText().toString());
        bundle.putString("to", name2.getText().toString());

//
//        fragment1.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Operator Recommend"));
        tabs.addTab(tabs.newTab().setText("WiFi Recommend"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if (position == 0)
                    selected = fragment2;
                else if (position == 1)
                    selected = fragment1;
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
                    if(str.equals("구름많음"))
                        str = "흐림";
                    if(str.equals("구름조금"))
                        str = "맑음";

                    switch (str){
                        case "맑음":
                            str = "Sunny";
                            break;
                        case "흐림":
                            str = "Cloudy";
                            break;
                        case "눈":
                            str = "Snowy";
                            break;
                        case "비":
                            str = "Rainy";
                            break;

                        default:
                            str = "Sunny";
                            break;
                    }
                    //날씨를 Bundle 객체로 보내기 위해 String 타입변수로 저장
                    String weatherText = str;//날씨
                    String tempText = tmp[1].substring(0, 2); //온도
                    String dayText = tmp2[0]; //요일

                    //어댑터를 통해 현재의 context로 DB를 불러온다.
                    mDbHelper = new DataAdapter(getApplicationContext());
                    mDbHelper.createDatabase();
                    mDbHelper.open();

                    //출발역, 도착역, 날씨, 요일, 시간 정보를 바탕으로 subwayData를 추출한다.
                    subwayData = mDbHelper.getTableData(fromCode, toCode, weatherText, dayText, Hours);
                    SubwaySendList subwaySendList = new SubwaySendList();
                    subwaySendList.setDataList((ArrayList<Data>) subwayData);

                    //넘어온 객체들이 저장되어있는 데이터 리스트형식

                    //어레이 리스트를 번들에 넣고 최종적으로 fragment1로 subwaydata 넘김
                    bundle.putParcelableArrayList("subwayData", (ArrayList<? extends Parcelable>) subwayData);
                    fragment1.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

                    int size = subwayData.size();
                    System.out.println("총 와이파이 개수 : " + size);
                    System.out.println("출발역 : " + fromName + "도착역 : " + toName);

//                    for(int i=0;i<size;i++){
//                        Data data = subwayData.get(i);
//                        System.out.println("정보 :  "+ data.getStation() +" / "+ data.getSsid()+" / "+data.getScore());
//                    }

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