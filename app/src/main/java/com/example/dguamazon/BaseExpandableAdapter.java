package com.example.dguamazon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {
    private ArrayList<String> rootStation = null;
    private ArrayList<ArrayList<String>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;
    private Data data = new Data();

    public BaseExpandableAdapter(Context c, ArrayList<String> rootStation, ArrayList<ArrayList<String>> childList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.rootStation = rootStation;
        this.childList = childList;
    }

    // 그룹 포지션을 반환한다.
    @Override
    public String getGroup(int groupPosition) {
        return rootStation.get(groupPosition);
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() { return rootStation.size(); }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) { return groupPosition; }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_parent, parent, false);
            viewHolder.tv_groupName = (TextView) v.findViewById(R.id.tv_group);
            viewHolder.tv_traffic = (ImageView) v.findViewById(R.id.tv_traffic);
            viewHolder.imageView = (ImageView) v.findViewById(R.id.indicator);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.tv_groupName.setText(getGroup(groupPosition));

//        이 부분에서 rootStation 가져오고 data도 가져와서 역마다 onesubway만들고 1등이 0.~~ 넘으면
//        OR 1등와이파이 계산을 클릭드에서 하고 FRAGMENT로 넘어와서 그 1등..? 으아ㅡ낭르ㅏ



        int rd = (int) (Math.random()*3 + 1);

        if(viewHolder.tv_groupName.toString().equals("Wansimni"))
            viewHolder.tv_traffic.setImageResource(R.drawable.greencolor);

//        else if(rd == 1)
//            viewHolder.tv_traffic.setImageResource(R.drawable.greencolor);
//        else if(rd == 2)
//            viewHolder.tv_traffic.setImageResource(R.drawable.orangecolor);
        else
            viewHolder.tv_traffic.setImageResource(R.drawable.redcolor);

//        펼쳐지는 화살표
        if(isExpanded){
            viewHolder.imageView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }

        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition); }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) { return childPosition; }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row, null);
            viewHolder.tv_childName = (TextView) v.findViewById(R.id.tv_child);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));

        return v;
    }

    @Override
    public boolean hasStableIds() {	return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {
        public TextView tv_groupName;
        public ImageView tv_traffic;
        public ImageView imageView;
        public TextView tv_childName;
    }
}