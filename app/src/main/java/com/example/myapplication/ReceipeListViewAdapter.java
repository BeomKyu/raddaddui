package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ReceipeListViewAdapter extends BaseAdapter{

        Context mContext = null;
        LayoutInflater mLayoutInflater = null;
        ArrayList<IngredientData> dataArrayList;

        public ReceipeListViewAdapter(Context context, ArrayList<IngredientData> data) {
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
            View view = mLayoutInflater.inflate(R.layout.receipe_list_view, null);

            //카테고리, 수량, 보관위치, 구매일자 추가
            ImageView imageView = (ImageView)view.findViewById(R.id.receipeimg);
            TextView title = (TextView)view.findViewById(R.id.title);

            Glide.with(view).load(dataArrayList.get(position).getReceipeImg()).fitCenter().into(imageView);
            Log.d("Tag", dataArrayList.get(position).getReceipeImg());
            title.setText(dataArrayList.get(position).getTitle());

            return view;
        }
}

