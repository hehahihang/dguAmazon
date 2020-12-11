package com.example.dguamazon;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {
    private Fragment1Listener listener;
    public interface Fragment1Listener{
        void onInputSent(String telecomName, int stationSize);
    }

    Resources res = new Resources();
    ArrayList<String> totalStation = new ArrayList<>(Arrays.asList(res.name));
    protected ArrayList<String> rootStation = null;
    private ArrayList<ArrayList<String>> mChildList = null;


    SubwaySendList subwaySendList = null;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int lgCnt = 0;
        int lgFreeCnt = 0;

        int ktCnt = 0;
        int ktFreeCnt = 0;

        int skCnt = 0;

        int max = 0;

        int stationSize = 0;
        int stationSize2 = 0;

        int ktScore = 0;
        int ktFreeScore = 0;
        int skScore = 0;
        int lgScore = 0;
        int lgFreeScore = 0;


//        나중에 산점도 해서 값 바꿔라 오희경 정신차려
        double maxCutoff = 0;
        double minCutoff = 0;

        rootStation = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

        ExpandableListView elv = (ExpandableListView) rootView.findViewById(R.id.list);
        elv.setAdapter(new BaseExpandableAdapter(getActivity(), rootStation, mChildList));

        String from = getArguments().getString("from");
        String to = getArguments().getString("to");

//      경로 내 subwaydata 넘기기
        ArrayList subwayData = getArguments().getParcelableArrayList("subwayData");
        System.out.println(subwayData.size());

//      양방향
        if (totalStation.indexOf(from) < totalStation.indexOf(to)) {
            int rootDist = totalStation.indexOf(to) - totalStation.indexOf(from);
            if (rootDist <= 21) {
                for (int i = totalStation.indexOf(from); i <= totalStation.indexOf(to); i++)
                    if(i != 0)
                        rootStation.add(res.name[i]);
            } else {
                for (int i = totalStation.indexOf(from); i >= 1; i--)
                    rootStation.add(res.name[i]);
                for (int i = totalStation.size() - 1; i >= totalStation.indexOf(to); i--)
                    rootStation.add(res.name[i]);
            }
        } else if (totalStation.indexOf(from) > totalStation.indexOf(to)) {
            int rootDist = totalStation.indexOf(from) - totalStation.indexOf(to);
            if (rootDist <= 21) {
                for (int i = totalStation.indexOf(from); i >= totalStation.indexOf(to); i--)
                    if(i != 0)
                        rootStation.add(res.name[i]);
            } else {
                for (int i = totalStation.indexOf(from); i <= totalStation.size() - 1; i++)
                    rootStation.add(res.name[i]);
                for (int i = 1; i <= totalStation.indexOf(to); i++)
                    rootStation.add(res.name[i]);
            }
        }

        Comparator<Data> scoreComparator = new Comparator<Data>(){

            @Override
            public int compare(Data t1, Data t2) {
                double delta = t2.getScore() - t1.getScore();
                if(delta > 0 ) return 1;
                if(delta < 0) return -1;
                return 0;
            }
        };

        //1등의 개수를 세서,
        for(int i = 0; i< rootStation.size(); i++){
            ArrayList<Data> oneSubway = new ArrayList<>();
            ArrayList<String> mChildListContent = new ArrayList<>();
            System.out.println("역 이름 : " + rootStation.get(i));

            //그 역 이름이랑 getStation이랑 같은 data들만 모은다.
            for(int j = 0; j < subwayData.size(); j++){
                Data data = (Data) subwayData.get(j);
                System.out.println(data.getStation()+"/"+data.getSsid()+"/"+data.getScore()+"/");
                //rootStation의 i번째 인덱스 역이랑 이름이 같은 data만 oneSubway에 모은다.
                if(data.getStation().equals(rootStation.get(i))){
                    oneSubway.add(data);

                }
            }

            Collections.sort(oneSubway, scoreComparator);
            for(int p = 0; p<oneSubway.size(); p++)
                System.out.print(oneSubway.get(p).getStation()+"/"+oneSubway.get(p).getSsid()+"/"+oneSubway.get(p).getScore());

            stationSize = oneSubway.size();
            stationSize2 = rootStation.size();

            if(stationSize >= 3){
                for(int j = 0; j< 3; j++){
                    String ssidName = oneSubway.get(j).getSsid();
                    Double ssidScore = oneSubway.get(j).getScore();
                    if(ssidName.equals("SK_WiFi")){
                        if(ssidScore>=maxCutoff){
                            skScore += 3;
                        }
                        else if(ssidScore<=minCutoff){
                            skScore += 1;
                        }
                        else{
                            skScore += 2;
                        }
                        skCnt++;
                    }
                    else if(ssidName.equals("U_WiFi")){
                        if(ssidScore>=maxCutoff){
                            lgScore += 3;
                        }
                        else if(ssidScore<=minCutoff){
                            lgScore += 2 ;
                        }
                        else{
                            lgScore += 1;
                        }
                        lgCnt++;
                    }
                    else if(ssidName.equals("Free_U_WiFi")){
                        if(ssidScore>=maxCutoff){
                            lgFreeScore += 3;
                        }
                        else if(ssidScore<=minCutoff){
                            lgFreeScore += 2;
                        }
                        else{
                            lgFreeScore += 1;
                        }
                        lgFreeCnt++;
                    }
                    else if(ssidName.equals("KT_Free_WiFi")){
                        if(ssidScore>=maxCutoff){
                            ktFreeScore += 3;
                        }
                        else if(ssidScore<=minCutoff){
                            ktFreeScore += 2;
                        }
                        else{
                            ktFreeScore += 1;
                        }
                        ktFreeCnt++;
                    }

                    else if(ssidName.equals("KT_WiFi")){
                        if(ssidScore>=maxCutoff){
                            ktScore += 3;
                        }
                        else if(ssidScore<=minCutoff){
                            ktScore += 2;
                        }
                        else{
                            ktScore += 1;
                        }
                        ktCnt++;
                    }
                    mChildListContent.add(ssidName);
                }
            }

            String telecomName = "";
            max = Math.max(skCnt,Math.max(ktCnt,lgCnt));

            if(skCnt==max)
                telecomName = "SK_WiFi";
            else if (ktFreeCnt == max)
                telecomName = "KT_Free_WiFi";
            else if(lgFreeCnt == max)
                telecomName = "U_Free_WiFi";
            else if (ktCnt==max)
                telecomName = "KT_WiFi";
            else if (lgCnt==max)
                telecomName = "U_WiFi";

            System.out.println("sk : "+ skCnt+"개 "+"kt : "+ktCnt+"개 "+"ktfree : "+ktFreeCnt+"개 "+"lg : "+lgCnt+"개 "+"lgfree : "+lgFreeCnt+"개 ");
            System.out.println("sk : "+ skScore+"점 "+"kt : "+ktScore+"점 "+"ktFREE : "+ktFreeScore+"점 "+"lg : "+lgScore+"점 "+"lgfree : "+lgFreeScore+"점 ");

            listener.onInputSent(telecomName,stationSize2);
            mChildList.add(mChildListContent);
        }

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1, rootStation);

//        listView.setAdapter(listViewAdapter);
//        elv.setAdapter(new SavedTabsListAdapter());

        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
        elv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Fragment1Listener){
            listener = (Fragment1Listener) context;
        }
        else{
            throw new RuntimeException(context.toString()
            +" must implement Framgment1Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}