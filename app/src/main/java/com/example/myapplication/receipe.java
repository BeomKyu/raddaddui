package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class receipe {

    private String MANUAL01;
    private String RCP_PARTS_DTLS;
    private String RCP_NM;

    public receipe(JSONObject jsonObject){
        try {
            // 아래의 코드로 원하는 항목 추가가능
//            this. = jsonObject.getString("");
            this.RCP_PARTS_DTLS = jsonObject.getString("RCP_PARTS_DTLS");
            this.RCP_NM = jsonObject.getString("RCP_NM");
            this.MANUAL01 = jsonObject.getString("MANUAL01");
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
    public String getMANUAL01(){
        return MANUAL01;
    }
}
