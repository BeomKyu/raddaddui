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

        IngredientDataList = new ArrayList<IngredientData>();

        ListView listView = (ListView) findViewById( R.id.listview);
        final ListViewAdapter listViewAdapter = new ListViewAdapter(this, IngredientDataList);

        listView.setAdapter(listViewAdapter);

        addFirebase.listen_document_multiple(new MyOnceCallBack() {
            @Override
            public void onCallback(List<Map<String, Object>> value) {
                for(int i = 0 ; i < value.size(); i++) {
                    Log.d("MyTag", value.get(i).get("Id").toString());

                    IngredientDataList.add(new IngredientData(R.drawable.ingredients, value.get(i).get("재료명").toString(), value.get(i).get("유통기한").toString()));

                }
                listViewAdapter.notifyDataSetChanged();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), listViewAdapter.getItem(i).getTrademark(), Toast.LENGTH_LONG).show();
            }
        });

    }

}