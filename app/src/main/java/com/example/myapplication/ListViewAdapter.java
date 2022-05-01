package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        //카테고리, 수량, 보관위치, 구매일자 추가
        ImageView imageView = (ImageView)view.findViewById(R.id.img);
        TextView movieName = (TextView)view.findViewById(R.id.trademark);
        TextView grade = (TextView)view.findViewById(R.id.expirationdate);

        imageView.setImageResource(dataArrayList.get(position).getImg());
        movieName.setText(dataArrayList.get(position).getTrademark());
        grade.setText(dataArrayList.get(position).getExpirationdate());

        return view;
    }
}
