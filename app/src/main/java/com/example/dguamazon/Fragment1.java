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

//    public class SavedTabsListAdapter extends BaseExpandableListAdapter {
//
//        private String[] groups = { "People Names", "Dog Names", "Cat Names", "Fish Names" };
//
//        private String[][] children = {
//                { "Arnold", "Barry", "Chuck", "David" },
//                { "Ace", "Bandit", "Cha-Cha", "Deuce" },
//                { "Fluffy", "Snuggles" },
//                { "Goldy", "Bubbles" }
//        };
//
//        @Override
//        public int getGroupCount() {
//            return groups.length;
//        }
//
//        @Override
//        public int getChildrenCount(int i) {
//            return children[i].length;
//        }
//
//        @Override
//        public Object getGroup(int i) {
//            return groups[i];
//        }
//
//        @Override
//        public Object getChild(int i, int i1) {
//            return children[i][i1];
//        }
//
//        @Override
//        public long getGroupId(int i) {
//            return i;
//        }
//
//        @Override
//        public long getChildId(int i, int i1) {
//            return i1;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//        @Override
//        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
//            TextView textView = new TextView(Fragment1.this.getActivity());
//            textView.setText(getGroup(i).toString());
//            return textView;
//        }
//
//        @Override
//        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
//            TextView textView = new TextView(Fragment1.this.getActivity());
//            textView.setText(getChild(i, i1).toString());
//            return textView;
//        }
//
//        @Override
//        public boolean isChildSelectable(int i, int i1) {
//            return true;
//        }
//
//    }

}

//public class Fragment1 extends ListFragment {
//
//    Resources res = new Resources();
//     ArrayList<String> rootStation = null;
//
//    @SuppressLint("WrongConstant")
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        super.onActivityCreated(savedInstanceState);
//
//
//        rootStation = new ArrayList<>();
//        int fromIndex = 0;
//        for (int i = 0; i < res.name.length; i++) {
//            rootStation.add(res.name[i]);
//        }
//        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, rootStation));
//
//        String name = getArguments().getString("name");
//
//    }
//
//    @SuppressLint("WrongConstant")
//    @Override
//    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        String toast = rootStation.get(position);
//        Toast.makeText(getActivity(), toast, 0).show();
//    }
//}