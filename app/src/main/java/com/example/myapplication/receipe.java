package com.example.myapplication;

import android.media.Image;

import org.json.JSONException;
import org.json.JSONObject;

public class receipe {
//
//    private String MANUAL01;
//    private String MANUAL07;
    private String RCP_PARTS_DTLS;
    private String RCP_NM;
    private String[] MANUAL = new String[20];
    private String ATT_FILE_NO_MAIN;

    public receipe(JSONObject jsonObject){
        try {
            // 아래의 코드로 원하는 항목 추가가능
//            this. = jsonObject.getString("");
            this.RCP_PARTS_DTLS = jsonObject.getString("RCP_PARTS_DTLS");
            this.RCP_NM = jsonObject.getString("RCP_NM");
            for(int i = 0; i < 9; i++) {
                this.MANUAL[i] = jsonObject.getString("MANUAL0" + (i+1));
            }
            for(int i = 9; i < 20; i++){
                this.MANUAL[i] = jsonObject.getString("MANUAL" + (i+1));
            }
            this.ATT_FILE_NO_MAIN = jsonObject.getString("ATT_FILE_NO_MAIN");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    아래의 getter 코드 사용
//    public String get(){
//        return ;
//    }

    public String getRCP_NM(){
        return RCP_NM;
    }
    public String getRCP_PARTS_DTLS(){
        return RCP_PARTS_DTLS;
    }
    public String[] getMANUAL(){
        return MANUAL;
    }
    public String getATT_FILE_NO_MAIN(){
        return ATT_FILE_NO_MAIN;
    }
}
