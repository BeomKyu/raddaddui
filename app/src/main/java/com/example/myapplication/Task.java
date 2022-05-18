package com.example.myapplication;

import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Task extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;

    private final String rcp_nm;
    public Task(String rcp_nm){
        super();
        this.rcp_nm = rcp_nm;
    }
    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        try{
            if(this.rcp_nm != null) {
                url = new URL("https://openapi.foodsafetykorea.go.kr/api/0d01938551d64498a2db/COOKRCP01/json/1/10/RCP_NM=" + this.rcp_nm);
                Log.d("MYTAG", this.rcp_nm);
            }
            else
                url = new URL("https://openapi.foodsafetykorea.go.kr/api/0d01938551d64498a2db/COOKRCP01/json/1/10/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");

            if(conn.getResponseCode() == conn.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while((str = reader.readLine()) != null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                reader.close();
            }
            else{
                Log.d("MYTAG", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return receiveMsg;
    }
}
