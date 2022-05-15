package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecipeActivity extends AppCompatActivity {

    Button serch_btn;
    EditText serch_txt;
    public TextView serched_txt;
    String resultTxt = "";
    Boolean aBoolean = false;

    List<receipe> rcpList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        serch_btn = (Button)findViewById(R.id.receipe_serch);
        serch_txt = (EditText)findViewById(R.id.receipe_serch_txt);
        serched_txt = (TextView)findViewById(R.id.receipe_serched_txt);
        serch_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                aBoolean = true;
            }
        });
        serch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serch_receipe_with_txt();
                if(rcpList.size() != 0){

                }
            }
        });

    }

    private void serch_receipe_with_txt(){
        rcpList = new ArrayList();
        try{
            if(aBoolean) {
                resultTxt = new Task(serch_txt.getText().toString()).execute().get();
            }
            else
                resultTxt = new Task(null).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        jsonParsing(resultTxt);
        Log.d("MYTAG", String.valueOf(rcpList.size()));
    }

    private void jsonParsing(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONObject rcpObject = jsonObject.getJSONObject("COOKRCP01");
            JSONObject infoObject = rcpObject.getJSONObject("RESULT");
            Log.d("MYTAG", infoObject.getString("CODE"));
            if(infoObject.getString("CODE").equals("INFO-000")){
                JSONArray rcpArray = rcpObject.getJSONArray("row");
                for(int i = 0; i < rcpArray.length(); i++){
                    JSONObject rcpObject1 = rcpArray.getJSONObject(i);

                    receipe receipe1 = new receipe(rcpObject1);

                    rcpList.add(receipe1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("MYTAG", String.valueOf(e));
        }
    }
}