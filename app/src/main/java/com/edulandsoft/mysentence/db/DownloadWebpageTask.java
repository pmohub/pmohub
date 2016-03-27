package com.edulandsoft.mysentence.db;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWebpageTask extends AsyncTask<String, Integer,String> {

    String TAG = "psh DownloadWebpageTask";

    public String mResult, mParams;

    protected String doInBackground(String... urls) {

        Log.d(TAG, "doInBackground");

        StringBuilder jsonHtml = new StringBuilder();
        try{
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(mParams);
            wr.flush();
            wr.close();

            int response = conn.getResponseCode();

            if(response == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                for(;;){
                    String line = br.readLine();
                    if(line == null) break;
                    jsonHtml.append(line + "\n");
                }
                br.close();
                conn.disconnect();
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }

        Log.d(TAG, "doInBackground="+jsonHtml.toString());

        return jsonHtml.toString();
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG,"onPostExecute="+result);
        mResult = result;
    }

}