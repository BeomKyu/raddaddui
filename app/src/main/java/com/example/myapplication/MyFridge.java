package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyFridge extends AppCompatActivity {

    ArrayList<IngredientData> IngredientDataList;
    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fridge);

        RadioGroup StoragePos = (RadioGroup)findViewById(R.id.myStoragePos);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

        IngredientDataList = new ArrayList<IngredientData>();

        ListView listView = (ListView) findViewById(R.id.listview);
        final ListViewAdapter listViewAdapter = new ListViewAdapter(this, IngredientDataList);

        listView.setAdapter(listViewAdapter);

        StoragePos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.mycold){
                    addFirebase.listen_document_multiple(new MyOnceCallBack() {
                        @Override
                        public void onCallback(List<Map<String, Object>> value) {
                            IngredientDataList.clear();
                            for (int i = 0; i < value.size(); i++) {
                                Log.d("MyTag", value.get(i).get("Id").toString());

                                String expirationdate = simpleDateFormat.format(value.get(i).get("유통기한"));

                                if(value.get(i).get("보관위치").toString().equals("냉장")){
                                    IngredientDataList.add(new IngredientData(R.drawable.ingredients, value.get(i).get("상품명").toString(), expirationdate));
                                }
                                //IngredientDataList.add(new IngredientData(R.drawable.ingredients, value.get(i).get("상품명").toString(), expirationdate));

                            }
                            listViewAdapter.notifyDataSetChanged();
                        }
                    });
                }else if(i == R.id.myfreeze){
                    addFirebase.listen_document_multiple(new MyOnceCallBack() {
                        @Override
                        public void onCallback(List<Map<String, Object>> value) {
                            IngredientDataList.clear();
                            for (int i = 0; i < value.size(); i++) {
                                Log.d("MyTag", value.get(i).get("Id").toString());

                                String expirationdate = simpleDateFormat.format(value.get(i).get("유통기한"));

                                if(value.get(i).get("보관위치").toString().equals("냉동")){
                                    IngredientDataList.add(new IngredientData(R.drawable.ingredients, value.get(i).get("상품명").toString(), expirationdate));
                                }
                                //IngredientDataList.add(new IngredientData(R.drawable.ingredients, value.get(i).get("상품명").toString(), expirationdate));

                            }
                            listViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        if(flag == true){
            addFirebase.listen_document_multiple(new MyOnceCallBack() {
                @Override
                public void onCallback(List<Map<String, Object>> value) {
                    IngredientDataList.clear();
                    for (int i = 0; i < value.size(); i++) {
                        Log.d("MyTag", value.get(i).get("Id").toString());

                        String expirationdate = simpleDateFormat.format(value.get(i).get("유통기한"));

                        if(value.get(i).get("보관위치").toString().equals("냉장")){
                            IngredientDataList.add(new IngredientData(R.drawable.ingredients, value.get(i).get("상품명").toString(), expirationdate));
                        }
                        //IngredientDataList.add(new IngredientData(R.drawable.ingredients, value.get(i).get("상품명").toString(), expirationdate));

                    }
                    listViewAdapter.notifyDataSetChanged();
                }
            });
            flag = false;
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), listViewAdapter.getItem(i).getTrademark(), Toast.LENGTH_LONG).show();
            }
        });

    }

}