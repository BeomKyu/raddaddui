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

public class Recipemain extends AppCompatActivity {

    String Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipemain);

        Intent intent = getIntent();

        TextView recipetitle = (TextView)findViewById(R.id.recipetitle);
        TextView manual = (TextView)findViewById(R.id.manual);
        ImageView receipeImg = (ImageView)findViewById(R.id.receipeImg);

        recipetitle.setText(intent.getStringExtra("RecipeID"));
        Glide.with(this).load(intent.getStringExtra("RecipeImg")).fitCenter().into(receipeImg);


    }

}