package com.example.dguamazon;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootStation = new ArrayList<>();


        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);


//        ExpandableListView elv = (ExpandableListView) rootView.findViewById(R.id.list);
//        elv.setAdapter(new SavedTabsListAdapter());

        ListView listView = rootView.findViewById(R.id.wifiList);

//        출발역 데이터 넘겼다리ㅜ
        String from = getArguments().getString("from");
        System.out.println(from);

//      도착역 받기만 하면돼! 호호!!
//      String dep = getArguments().getString("dep");
//      System.out.println(dep);


//      스타트 역 받아서 그 역부터 리스트 출력성공 ㅜ
        for(int i= totalStation.indexOf(from); i< res.name.length; i++){
            rootStation.add(res.name[i]);
        }

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1, rootStation);

        listView.setAdapter(listViewAdapter);

        return rootView;

    }
}