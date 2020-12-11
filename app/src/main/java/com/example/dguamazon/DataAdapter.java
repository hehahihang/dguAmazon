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

    //(정방향)fromCode가 작을때
    public List getTableData(int fromCode, int toCode, String weatherText, String dayText, String Hours){

        try{
            //조건에 따라 출력하는 SQL쿼리
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE (code BETWEEN ? AND ? ) AND (weather = ? AND days = ? AND hours = ?)" ;
            String [] args = {Integer.toString(fromCode), Integer.toString(toCode), weatherText, dayText, Hours};

            List<Data> subwayList = new ArrayList();
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
                    data.setCode(Integer.parseInt(mCur.getString(6)));

                    subwayList.add(data);
                }
            }
            return subwayList; //여기서 리턴했는데 이걸 담아서 보내주면 되나

        }
        catch (SQLException mSQLException){
            Log.e(TAG,"getTestData >> "+mSQLException.toString());
            throw mSQLException;
        }
    }
}