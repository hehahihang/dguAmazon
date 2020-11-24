package com.example.dguamazon;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment1 extends Fragment {
    Resources res = new Resources();
    ArrayList<String> totalStation = new ArrayList<>(Arrays.asList(res.name));
    private ArrayList<String> rootStation = null;

    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent = null;

    SubwaySendList subwaySendList;


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
        System.out.println(from);

//      도착역 받기만 하면돼! 호호!!
        String to= getArguments().getString("to");
        System.out.println(to);

//      스타트 역 받아서 그 역부터 리스트 출력성공 ㅜ
        for(int i= totalStation.indexOf(from); i<= totalStation.indexOf(to); i++){
            rootStation.add(res.name[i]);
        }
        //여기가 문제 이거 순서대로만 출력 가능할 듯

        mChildListContent.add("1");
        mChildListContent.add("2");
        mChildListContent.add("3");

        for(int i=0;i<=rootStation.size(); i++){
            mChildList.add(mChildListContent);
        }

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, rootStation);

//        listView.setAdapter(listViewAdapter);
//        elv.setAdapter(new SavedTabsListAdapter());

        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "g click = " + groupPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "c click = " + childPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        elv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(), "g Collapse = " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(), "g Expand = " + groupPosition, Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}