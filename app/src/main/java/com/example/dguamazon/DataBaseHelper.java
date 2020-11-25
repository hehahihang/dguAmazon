package com.example.dguamazon;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.Nullable;

    public class DataBaseHelper extends SQLiteOpenHelper {

        private static String TAG = "DataBaseHelper";
        private static String DB_PATH = "";
        private static String DB_NAME = "subway.db";
        private SQLiteDatabase mDatabase;
        private final Context mContext;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null,1);
        if(Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else{
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    public void createDataBase() throws IOException{
        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist){
            this.getReadableDatabase();
            this.close();
            try{
                copyDataBase(); //DB가 없으므로 Asset 폴더에서 복사해온다.
                Log.e(TAG,"createDataBase : database created");
            }
            catch (IOException e){
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    public boolean checkDataBase(){
        File dbFile = new File(DB_PATH+DB_NAME);
        return dbFile.exists();
    }

    //내가 assets 폴더에 저장해둔 DB를 불러오는 과정
    public void copyDataBase() throws IOException{
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte [] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0){
            mOutput.write(mBuffer,0,mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //데이터 베이스를 열어서 쿼리를 사용할 수 있게 만드는 과정
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDatabase != null;
    }

    @Override
    public synchronized void close() {
        if(mDatabase!=null){
            mDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }
}
