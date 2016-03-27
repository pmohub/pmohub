package com.edulandsoft.mysentence.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.edulandsoft.mysentence.common.g;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Dbo {

    static Dbo dbo; DatabaseHelper dbHelper; SQLiteDatabase db; Context context;
    String query;
    Cursor rs;


    String TAG = "psh Dbo";
    public static String DATABASE_NAME = "me0100100400015kr1.db";
    public static int DATABASE_VERSION = 1;

    public  Dbo(Context context) {
        this.context = context;

        try {
            boolean bResult = isCheckDB(context);  // DB가 있는지?
            Log.d(TAG, "DB Check="+bResult);
            if(!bResult){   // DB가 없으면 복사
                copyDB(context);
            }else{}
        } catch (Exception e) {}

    }

    //-------------------------------------------------------------------------
    //                              mSentence
    //-------------------------------------------------------------------------

    public void InsSentenceT() {

        query  = "delete from mSentenceT  \n";
        Log.d(TAG, query);
        db.execSQL(query);

        query  = "insert into mSentenceT  \n";
        query += "select * from mSentence  \n";
        query += " where masterYn = 'N'    \n";
        query += " and delYn = 'N'         \n";
        query += " order by learnCnt asc, createDate desc, createtime asc, registNum asc \n";
        Log.d(TAG, query);
        db.execSQL(query);

        g.curIdx = 1;

        query  = "select count(*) cnt from mSentenceT \n";
        Log.d(TAG, query);
        rs = db.rawQuery(query, null); rs.moveToFirst();
        g.lastIdx = rs.getInt(rs.getColumnIndex("cnt"));
        Log.d(TAG, "g.lastIdx="+g.lastIdx);

        if(g.lastIdx < 1) g.lastIdx = 1;

    }

    public Cursor SelSentenceT(int rowid) {

        query  = "select * from mSentenceT \n";
        Log.d(TAG, query);
        rs = db.rawQuery(query, null);

        return rs;
    }

    public void DelSentence() {
        query = "delete from mSentence where userId <> 'admin'";
        Log.d(TAG, "DelSentence=" + query);
        db.execSQL(query);
    }

    public void InsSentence(int    registNum,
                            String registCode,
                            String keyword,
                            String mySentence,
                            String tranSentence,
                            int    learnCnt,
                            String masterYn,
                            String delYn,
                            String createDate,
                            String createTime,
                            String updateDate,
                            String updateTime
    )
    {
        query  = "insert into mSentence values(";
        query += " '"+g.googleId  +"'";
        query += ", "+registNum   +" ";
        query += ",'"+registCode  +"'";
        query += ",'"+keyword     +"'";
        query += ",'"+mySentence.replaceAll("'","''")  +"'";
        query += ",'"+tranSentence.replaceAll("'","''")+"'";
        query += ", "+learnCnt    +" ";
        query += ",'"+masterYn    +"'";
        query += ",'"+delYn       +"'";
        query += ",'"+createDate  +"'";
        query += ",'"+createTime  +"'";
        query += ",'"+updateDate  +"'";
        query += ",'"+updateTime  +"'";
        query += ")";

        Log.d(TAG, "InsSentence=" + query);
        db.execSQL(query);
    }


    public Cursor SelCnt() {
        String name;

        rs = db.rawQuery("select * from test01", null);
        /*
        rs.moveToFirst();
        while (!rs.isAfterLast()){
            name = rs.getString(0);

            Log.d(TAG, "ppppppppppppppppppppp="+name);
            rs.moveToNext();
        }
        */

        return rs;
    }

    public void InsCnt() {

        db.execSQL("insert into test01 values ('둘')");
    }


    //---------------------------------------------------------------------------------
    //  SQLiteDatabase
    //---------------------------------------------------------------------------------
    public static Dbo getInstance(Context context) {
        if (dbo == null) dbo = new Dbo(context);
        return dbo;
    }

    public boolean open() {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return true;
    }

    public void close() {
        db.close();
        dbo = null;
    }


    public boolean isCheckDB(Context mContext){
        String filePath = "/data/data/" + g.packageNm + "/databases/" + DATABASE_NAME;
        File file = new File(filePath);
        if (file.exists()) { return true; }
        return false;
    }

    public void copyDB(Context mContext){
        Log.d(TAG, "copyDB");
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/" + g.packageNm + "/databases";
        String filePath   = "/data/data/" + g.packageNm + "/databases/" + DATABASE_NAME;
        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            InputStream is = manager.open("db/" + DATABASE_NAME);
            BufferedInputStream bis = new BufferedInputStream(is);

            if (folder.exists()) {
            }else{
                folder.mkdirs();
            }


            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, read);
            }

            bos.flush();

            bos.close();
            fos.close();
            bis.close();
            is.close();

        } catch (IOException e) {
            Log.e("ErrorMessage : ", e.getMessage());
        }
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }
        public void onCreate(SQLiteDatabase db) {}
        public void onOpen(SQLiteDatabase db) {}
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
    }

}
