package com.example.dguamazon;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SubwaySendList implements Parcelable {
    ArrayList<Data> dataList;

    public SubwaySendList(){}

    protected SubwaySendList(Parcel in) {
        dataList = new ArrayList<>();
        in.readTypedList(dataList, Data.CREATOR);
    }

    public static final Creator<SubwaySendList> CREATOR = new Creator<SubwaySendList>() {
        @Override
        public SubwaySendList createFromParcel(Parcel in) {
            return new SubwaySendList(in);
        }

        @Override
        public SubwaySendList[] newArray(int size) {
            return new SubwaySendList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ArrayList<Data> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Data> dataList) {
        this.dataList = dataList;
    }
}
