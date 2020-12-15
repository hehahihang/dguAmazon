package com.example.dguamazon;


import android.app.Activity;
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
        void onInputSent(String telName, int stationSize, double max);

    }

    Resources res = new Resources();
    ArrayList<String> totalStation = new ArrayList<>(Arrays.asList(res.name));
    protected ArrayList<String> rootStation = null;
    private ArrayList<ArrayList<String>> mChildList = null;
    ArrayList<ArrayList<Data>> manyStation = null;
    static String telName = null;

    SubwaySendList subwaySendList = null;
    Bundle bundle;
    String telecomName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int stationSize = 0;
        int stationSize2 = 0;

        //0.566793  0.429575
        double maxCutoff = 0.566793;
        double minCutoff = 0.429575;

        double lgScore = 0;
        double lgFreeScore = 0;
        double ktScore = 0;
        double ktFreeScore = 0;
        double skScore = 0;

        rootStation = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        manyStation = new ArrayList<ArrayList<Data>>();
        telName = new String();
        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

        ExpandableListView elv = (ExpandableListView) rootView.findViewById(R.id.list);

        elv.setAdapter(new BaseExpandableAdapter(getActivity(), manyStation, mChildList, rootStation));

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
                    if (i != 0)
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

            //그 역 이름이랑 getStation이랑 같은 data들만 모은다.
            for(int j = 0; j < subwayData.size(); j++){
                Data data = (Data) subwayData.get(j);
                if(data.getStation().equals(rootStation.get(i))){
                    oneSubway.add(data);
                }
            }

            Collections.sort(oneSubway, scoreComparator);

            manyStation.add(oneSubway);
//            for(int q = 0; q < manyStation.size(); q++){
//                System.out.println("manySatation 역 이름은 : "+manyStation.get(q).get(0).getStation());
//            }

            stationSize = oneSubway.size();
            stationSize2 = rootStation.size();

            if(stationSize >= 3){
                for(int j = 0; j< 3; j++){
                    String ssidName = oneSubway.get(j).getSsid();
                    Double ssidScore = oneSubway.get(j).getScore();

                    if(ssidName.equals("SK_WiFi")){
                        skScore += ssidScore;
                    }
                    else if(ssidName.equals("KT_Free_WiFi")){
                        ktFreeScore += ssidScore;
                    }
                    else if(ssidName.equals("KT_Wifi")){
                        ktScore += ssidScore;
                    }
                    else if(ssidName.equals("U_WiFi")){
                        lgScore += ssidScore;
                    }
                    else if(ssidName.equals("U_Free_WiFi")){
                        lgFreeScore += ssidScore;
                    }
                    mChildListContent.add(ssidName);
                }
            }
            mChildList.add(mChildListContent);
        }

        telecomName = "error";

        skScore /= rootStation.size();
        ktScore /= rootStation.size();
        ktFreeScore /= rootStation.size();
        lgScore /= rootStation.size();
        lgFreeScore /= rootStation.size();

        double maxKT = Math.max(ktScore,ktFreeScore);
        double maxLG = Math.max(lgScore,lgFreeScore);
        double max = Math.max(skScore,Math.max(maxKT,maxLG));

        if (max == skScore) {
            telecomName = "SK_WiFi";
        }
        else if(max == ktScore){
            telecomName = "KT_WiFi";
        }
        else if(max == ktFreeScore){
            telecomName = "KT_Free_WiFi";
        }
        else if(max == lgScore){
            telecomName = "U_WiFi";
        }
        else if(max == lgFreeScore){
            telecomName = "U_Free_WiFi";
        }

        listener.onInputSent(telecomName,stationSize2,max);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1, rootStation);

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