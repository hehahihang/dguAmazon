package com.example.dguamazon;


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
    Resources res = new Resources();
    ArrayList<String> totalStation = new ArrayList<>(Arrays.asList(res.name));
    protected ArrayList<String> rootStation = null;
    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent = null;

    SubwaySendList subwaySendList = null;
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootStation = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList();


        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);


        ExpandableListView elv = (ExpandableListView) rootView.findViewById(R.id.list);
        elv.setAdapter(new BaseExpandableAdapter(getActivity(), rootStation, mChildList));

//        ListView listView = rootView.findViewById(R.id.wifiList);

//        출발역 데이터 넘겼다리ㅜ
        String from = getArguments().getString("from");
//        System.out.println(from);

//      도착역 받기만 하면돼! 호호!!
        String to = getArguments().getString("to");
//        System.out.println(to);

//      경로 내 subwaydata 넘기기
        ArrayList subwayData = getArguments().getParcelableArrayList("subwayData");
        System.out.println(subwayData.size());

//      양방향
        if (totalStation.indexOf(from) < totalStation.indexOf(to)) {
            int rootDist = totalStation.indexOf(to) - totalStation.indexOf(from);
            if (rootDist <= 21) {
                for (int i = totalStation.indexOf(from); i <= totalStation.indexOf(to); i++)
                    rootStation.add(res.name[i]);
            } else {
                for (int i = totalStation.indexOf(from); i >= 0; i--)
                    rootStation.add(res.name[i]);
                for (int i = totalStation.size() - 1; i >= totalStation.indexOf(to); i--)
                    rootStation.add(res.name[i]);
            }

        } else if (totalStation.indexOf(from) > totalStation.indexOf(to)) {
            int rootDist = totalStation.indexOf(from) - totalStation.indexOf(to);
            if (rootDist <= 21) {
                for (int i = totalStation.indexOf(from); i >= totalStation.indexOf(to); i--)
                    rootStation.add(res.name[i]);
            } else {
                for (int i = totalStation.indexOf(from); i <= totalStation.size() - 1; i++)
                    rootStation.add(res.name[i]);
                for (int i = 0; i <= totalStation.indexOf(to); i++)
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

        for(int i = 0; i< rootStation.size(); i++){
            mChildListContent.clear();
            ArrayList<Data> oneSubway = new ArrayList<>();

            System.out.println("역 이름 : " + rootStation.get(i));

            for(int j = 0; j < subwayData.size(); j++){
                Data data = (Data) subwayData.get(j);
                if(data.getStation().equals(rootStation.get(i)))
                    oneSubway.add(data);
            }
            Collections.sort(oneSubway, scoreComparator);

            if(oneSubway.size() >= 3){
                for(int k = 0; k<3; k++){
                    Data data = oneSubway.get(k);
                    mChildListContent.add(data.getSsid());
//                System.out.println(data.getStation()+" / "+data.getScore());
                }
            }
            else if(oneSubway.size() < 3 && oneSubway.size() != 0){
                for(int k = 0; k<oneSubway.size(); k++){
                    Data data = oneSubway.get(k);
                    mChildListContent.add(data.getSsid());
                }
            }
            else{
                mChildListContent.add("No WiFi");
            }

            System.out.println(mChildListContent);
            mChildList.add(mChildListContent);
        }
        System.out.println(mChildList.size());


//        mChildListContent.add("1st. SK_WiFi");
//        mChildListContent.add("2nd. KT_Free_WiFi");
//        mChildListContent.add("3rd. Free_U_WiFi");
//
//        for (int i = 0; i <= rootStation.size(); i++) {
//            mChildList.add(mChildListContent);
//        }

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
}