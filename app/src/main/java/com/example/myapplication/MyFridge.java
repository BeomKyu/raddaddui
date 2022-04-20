package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyFridge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fridge);

        Button test1btn = (Button) findViewById(R.id.test1_btn);
        Button test2btn = (Button) findViewById(R.id.test2_btn);

        TextView test3txt = (TextView) findViewById(R.id.test_3);
        TextView test4txt = (TextView) findViewById(R.id.test_4);
        TextView test5txt = (TextView) findViewById(R.id.test_5);
        TextView test6txt = (TextView) findViewById(R.id.test_6);
        TextView test7txt = (TextView) findViewById(R.id.test_7);

        addFirebase.listen_document_multiple(new MyCallBack() {
            @Override
            public void onCallback(List<Map<String, Object>> value) {

                //사용법 value.get(인덱스 번호).get("필드명");
                if(value.size() == 0)
                    Log.d("MyTag", "null");
                else{
                    test3txt.setText(value.get(0).get("상품명").toString());
                    test4txt.setText(value.get(0).get("재료명").toString());
                    test5txt.setText(value.get(0).get("유통기한").toString());
                    test6txt.setText(value.get(0).get("Id").toString());
                    Log.d("MyTag", "not null");
                }
            }
        });


        test1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("MyTag", String.valueOf(list.size()));
            }
        });
        test2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("MyTag", list.get(1));
            }
        });
    }
}