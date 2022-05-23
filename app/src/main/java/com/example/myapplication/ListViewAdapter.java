package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<IngredientData> dataArrayList;

    public ListViewAdapter(Context context, ArrayList<IngredientData> data) {
        mContext = context;
        dataArrayList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return dataArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public IngredientData getItem(int position) {
        return dataArrayList.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.list_view, null);

        String userID;
        //카테고리, 수량, 보관위치, 구매일자 추가
        ImageView imageView = (ImageView)view.findViewById(R.id.img);
        TextView movieName = (TextView)view.findViewById(R.id.trademark);
        TextView grade = (TextView)view.findViewById(R.id.expirationdate);
        Button delete = (Button)view.findViewById(R.id.delete);

        if(dataArrayList.get(position).getImg().equals("false")){
            imageView.setImageResource(R.drawable.ingredients);
        }else {
            Glide.with(view).load(dataArrayList.get(position).getImg()).into(imageView);
        }



        movieName.setText(dataArrayList.get(position).getTrademark());
        grade.setText(dataArrayList.get(position).getExpirationdate());

        userID = dataArrayList.get(position).getUserId();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFirebase.delete_one_ingredient_document(userID);
            }
        });

        return view;
    }
}
