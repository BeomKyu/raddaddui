package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Recipemain extends AppCompatActivity {

    String[] manuallist;
    String testlist = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipemain);

        Intent intent = getIntent();

        TextView recipetitle = (TextView)findViewById(R.id.recipetitle);
        TextView manual = (TextView)findViewById(R.id.manual);
        TextView partslist = (TextView)findViewById(R.id.partslist);
        ImageView receipeImg = (ImageView)findViewById(R.id.receipeImg);

        manuallist = intent.getStringArrayExtra("manual");
        recipetitle.setText(intent.getStringExtra("RecipeID"));
        Glide.with(this).load(intent.getStringExtra("RecipeImg")).override(800, 800).fitCenter().into(receipeImg);

        for(int i = 0; i < manuallist.length; i++){
            testlist += manuallist[i] + "\n";
        }

        partslist.setText(intent.getStringExtra("partslist"));
        manual.setText(testlist);

    }

}