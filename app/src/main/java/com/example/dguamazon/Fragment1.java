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
        System.out.println(from);

//      도착역 받기만 하면돼! 호호!!
        String to = getArguments().getString("to");
        System.out.println(to);

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


        mChildListContent.add("1st. SK_WiFi");
        mChildListContent.add("2nd. KT_Free_WiFi");
        mChildListContent.add("3rd. Free_U_WiFi");

        for (int i = 0; i <= rootStation.size(); i++) {
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
}