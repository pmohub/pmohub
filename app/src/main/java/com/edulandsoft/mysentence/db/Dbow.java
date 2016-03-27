package com.edulandsoft.mysentence.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.LinearLayout;

import com.edulandsoft.mysentence.common.g;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Dbow  extends LinearLayout {

    Dbo dbo; Cursor rs; Dbow dbow;
    Context context;
    private static final String TAG = "psh Dbow";

    public Dbow(Context context) {
        super(context);

        this.context = context;
        dbo = new Dbo(context);
    }

    //-----------------------------------------------------
    //                        User
    //-----------------------------------------------------
    public void SelUserInfo(){

        Log.d(TAG,"SelUserInfo");

        String params;

        try {
            DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
            params = "userId="+g.googleId;
            downloadWebpageTask.mParams = params;

            String result = downloadWebpageTask.execute("http://edulandsoft.cafe24.com/mysentence/SelUserInfo.asp").get();
            JSONArray ja = new JSONArray(result);
            Log.d(TAG,"SelUserInfo:JSONArray="+ja);
            int rowCnt = ja.length();

            g.isInternetReachability = true;

            if (rowCnt == 0) InsUserInfo();
            else {
                JSONObject UserInfo = ja.getJSONObject(0);

                Log.d(TAG,"SelUserInfo:buyYn="+UserInfo.getString("buyYn"));
                Log.d(TAG,"SelUserInfo:wwwDate="+UserInfo.getString("wwwDate"));
                Log.d(TAG,"SelUserInfo:curVersion="+UserInfo.getString("curVersion"));

                if (UserInfo.getString("buyYn") == "Y") {
                    g.isPurchase = true;
                    //AdMobAndroid.hideBanner(g.isPurchase);
                }
                g.wwwDate = UserInfo.getString("wwwDate");
                g.patchVersion = Integer.parseInt(UserInfo.getString("curVersion"));
            }

        } catch (InterruptedException e) { e.printStackTrace();
        } catch (ExecutionException e) { e.printStackTrace();
        } catch (JSONException e) { e.printStackTrace();
        }
    }

    public void InsUserInfo() {
        Log.d(TAG,"InsUserInfo");

        String params;

        try {
            DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
            params  = "userId="+g.googleId;
            params += "&userName="+g.googleName;
            params += "&fbId=";
            downloadWebpageTask.mParams = params;

            String result = downloadWebpageTask.execute("http://edulandsoft.cafe24.com/mysentence/InsUserInfo.asp").get();
            JSONArray ja = new JSONArray(result);
            Log.d(TAG,"InsUserInfo:JSONArray="+ja);

        } catch (InterruptedException e) { e.printStackTrace();
        } catch (ExecutionException e) { e.printStackTrace();
        } catch (JSONException e) { e.printStackTrace();
        }
    }

    //-----------------------------------------------------
    //                        UserLearnHis
    //-----------------------------------------------------
    public void SelUserLearnHis2()
    {
        Log.d(TAG, "SelUserLearnHis2");

        String params;

        try {
            DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
            params = "userId="+g.googleId;
            downloadWebpageTask.mParams = params;

            String result = downloadWebpageTask.execute("http://edulandsoft.cafe24.com/mysentence/SelUserLearnHis.asp").get();
            JSONArray ja = new JSONArray(result);
            Log.d(TAG, "SelUserLearnHis2:ja=" + ja);

            JSONObject UserLearnHis = ja.getJSONObject(0);
            Log.d(TAG, "SelUserLearnHis2 1111111111");
            g.todayLearnCnt = Integer.parseInt(UserLearnHis.getString("todayLearnCnt"));
            Log.d(TAG, "SelUserLearnHis2 2222222222");

        } catch (Exception e) { e.printStackTrace();
        } finally {}
    }


    //-----------------------------------------------------
    //                     Sentence
    //-----------------------------------------------------
    public void SelSentence(){

        Log.d(TAG, "SelSentence");

        String params;
        JSONObject sentence;

        try {
            DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
            params = "userId="+g.googleId;
            downloadWebpageTask.mParams = params;

            String result = downloadWebpageTask.execute("http://edulandsoft.cafe24.com/mysentence/SelSentence.asp").get();
            JSONArray ja = new JSONArray(result);
            Log.d(TAG,"SelSentence:ja="+ja);
            int rowCnt = ja.length();
            Log.d(TAG,"SelSentence:rowCnt="+rowCnt);

            g.isInternetReachability = true;

            if (rowCnt == 0) g.registNum = 1;
            else {
                dbo = Dbo.getInstance(this.context);
                Log.d(TAG, "SelSentence 11111111111111");
                dbo.open();
                Log.d(TAG, "SelSentence 22222222222222");
                dbo.DelSentence();
                Log.d(TAG, "SelSentence 33333333333333");

                for(int i=0; i<rowCnt;i++){
                    sentence = ja.getJSONObject(i);

                    dbo.InsSentence(
                            Integer.parseInt(sentence.getString("registNum")),
                            sentence.getString("registCode"),
                            sentence.getString("keyword"),
                            sentence.getString("mySentence"),
                            sentence.getString("tranSentence"),
                            Integer.parseInt(sentence.getString("learnCnt")),
                            sentence.getString("masterYn"),
                            sentence.getString("delYn"),
                            sentence.getString("createDate"),
                            sentence.getString("createTime"),
                            sentence.getString("updateDate"),
                            sentence.getString("updateTime")
                    );
                }
                dbo.close();
            }

        } catch (InterruptedException e) { e.printStackTrace();
        } catch (ExecutionException e) { e.printStackTrace();
        } catch (JSONException e) { e.printStackTrace();
        }
    }


    public void SelSentence2(){

        String strData = "";

        try {
            DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
            downloadWebpageTask.mParams = "userId=110217636682633959576";
            String result = downloadWebpageTask.execute("http://edulandsoft.cafe24.com/mysentence/SelUserInfo2.asp").get();

            JSONArray ja = new JSONArray(result);

            for(int i=0; i < ja.length(); i++) {
                // 개별 객체를 하나씩 추출
                JSONObject student = ja.getJSONObject(i);
                strData += student.getString("userId") + " - " + student.getString("userName") + "\n";
            }

            Log.d(g.TAG, "strData=" + strData);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
