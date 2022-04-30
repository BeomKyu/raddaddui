package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyFridge extends AppCompatActivity {

    ArrayList<IngredientData> IngredientDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fridge);

        this.IngredientData();

        ListView listView = (ListView) findViewById(R.id.listview);
        final ListViewAdapter listViewAdapter = new ListViewAdapter(this, IngredientDataList);

        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), listViewAdapter.getItem(i).getTrademark(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void IngredientData(){
        IngredientDataList = new ArrayList<IngredientData>();
        IngredientDataList.add(new IngredientData(R.drawable.ingredients,"asdasd","dasdas"));
        IngredientDataList.add(new IngredientData(R.drawable.ingredients,"dsadas","dasdas"));
        IngredientDataList.add(new IngredientData(R.drawable.ingredients,"ssss","dasdas"));
        IngredientDataList.add(new IngredientData(R.drawable.ingredients,"ssss","dasdas"));
        IngredientDataList.add(new IngredientData(R.drawable.ingredients,"ssss","dasdas"));
        IngredientDataList.add(new IngredientData(R.drawable.ingredients,"ssss","dasdas"));

        addFirebase.listen_document_multiple_once(new MyOnceCallBack() {
            @Override
            public void onCallback(List<Map<String, Object>> value) {
                for(int i = 0 ; i < value.size(); i++)
                    Log.d("MyTag", value.get(i).get("Id").toString());
            }
        });
//        addFirebase.listen_document_multiple(new MyCallBack() {
//            @Override
//            public void onCallback(List<Map<String, Object>> value) {
//                //사용법 value.get(인덱스 번호).get("필드명");
//                if (value.size() == 0){
//                    Log.d("MyTag", "null");
//                }else{
//                    Log.d("MyTag", "not null");
//                }
//            }
//        });
    }


}