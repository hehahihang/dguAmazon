package com.example.dguamazon;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter  {
    protected static final String TAG = "DataAdapter";
    protected static final String TABLE_NAME = "subway";


    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public DataAdapter(Context context){
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException{
        try{
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException){
            Log.e(TAG,mIOException.toString()+" UnableToCreateDatabase");
            throw new Error("UnableTocreateDataBase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException{
        try{
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException){
            Log.e(TAG,"open >>"+mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close(){
        mDbHelper.close();
    }

    public List getTableData(String fromName, String toName, String weatherText, String dayText, String Hours){
        try{
            //데이터를 넘길수는 있는데 어디 부터 어디 까지를 넘긴다는게 station이 일정하게 정렬되어있는게 아니라 어려움
            //station이름 말고 서브쿼리 사용해서 해당 스테이션 이름에 해당하는
            //id값을 불러와서 구간을 정하고 그 구간에 해당하는 데이터를 모두 가져와야 하나..

            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE (station = ? OR station = ?) AND weather = ? AND days = ? AND hours = ?";
            String [] args = {fromName, toName, weatherText, dayText, Hours};
            List subwayList = new ArrayList();
            Data data = null;
            Cursor mCur = mDb.rawQuery(sql,args);
            if(mCur!=null){
                while(mCur.moveToNext()){
                    data = new Data();
                    data.setStation(mCur.getString(0));
                    data.setDays(mCur.getString(1));
                    data.setWeather(mCur.getString(2));
                    data.setHours(mCur.getString(3));
                    data.setSsid(mCur.getString(4));
                    data.setScore(Double.parseDouble(mCur.getString(5)));

                    subwayList.add(data);
                }
            }
            return subwayList;
        }
        catch (SQLException mSQLException){
            Log.e(TAG,"getTestData >> "+mSQLException.toString());
            throw mSQLException;
        }
    }
}