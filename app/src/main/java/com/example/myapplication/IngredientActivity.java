package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IngredientActivity extends AppCompatActivity {

    String category; //카테고리
    String storpos; //저장위치
    ImageButton camera_btn; //카메라 버튼 및 이미지 미리보기 뷰

    int year = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH);
    int dayofmonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    final static int TAKE_PICTURE = 1;

    final static int REQUEST_TAKE_PHOTO = 1;
    private boolean imageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ingredient);

        Button Registerbtn = (Button) findViewById(R.id.Registerbtn);  //틍록하기
        ImageButton Buy_date_picker = (ImageButton) findViewById(R.id.buy_date_picker); //구매날짜달력
        ImageButton Epd_Date_picker = (ImageButton) findViewById(R.id.epd_Date_picker); //유통기한달력 
        Spinner Category = (Spinner) findViewById(R.id.category);  //카테고리스피너
        EditText Tradeame = (EditText) findViewById(R.id.tradename); //상품명
        RadioGroup StoragePos = (RadioGroup) findViewById(R.id.storagepos); //보관위치
        RadioButton coldbtn = (RadioButton)findViewById(R.id.cold); //냉장
        EditText Buydate = (EditText) findViewById(R.id.buydate); //구매날짜
        EditText Expirationdate = (EditText)findViewById(R.id.expirationdate); //유통기한

        camera_btn = (ImageButton)findViewById(R.id.ingredient_img);

        Buydate.setText(getDate()); //구매일자 초기 날짜
        Expirationdate.setText(getDate()); //유통기한 초기 날짜
        Tradeame.setTextColor(Color.BLACK);

        //카테고리
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category.setAdapter(adapter);

        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                category = adapterView.getItemAtPosition(i).toString(); //선택된 값 가져오기
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //보관위치
        if (coldbtn.isChecked() == true){
            storpos = "냉장";
        }
        StoragePos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.cold){
                    //냉장
                    storpos = "냉장";
                }else {
                    //냉동
                    storpos = "냉동";
                }
            }
        });
        
        //달력다이얼로그
        //구매날짜달력
        DatePickerDialog.OnDateSetListener buydateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Buydate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }

        };

        DatePickerDialog buydatePickerDialog = new DatePickerDialog(this, buydateSetListener, year, month, dayofmonth);

        Buy_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buydatePickerDialog.show();
            }
        });

        //유통기한날짜달력
        DatePickerDialog.OnDateSetListener epddateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Expirationdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }

        };

        DatePickerDialog epddatePickerDialog = new DatePickerDialog(this, epddateSetListener, year, month, dayofmonth);

        Epd_Date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                epddatePickerDialog.show();
            }
        });


        //등록하기 버튼
        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = new Date();
                Date date2 = new Date();
                try {
                    date1 = dateFormat.parse(Expirationdate.getText().toString());
                    date2 = dateFormat.parse(Buydate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long time1 = date1.getTime();
                long time2 = date2.getTime();
                Timestamp ts1 = new Timestamp(time1);
                Timestamp ts2 = new Timestamp(time2);
                addFirebase.add_new_ingredient(category, Tradeame.getText().toString(), ts1, null, ts2, storpos, imageChanged, camera_btn);

                Intent intent = new Intent(getApplicationContext(), MyFridge.class);
                startActivity(intent);
                finish();
            }
        });

        //카메라 버튼
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);


            }
        });
    }


    private String getDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getDate = dateFormat.format(date);
        return getDate;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            camera_btn.setImageBitmap(imageBitmap);
            imageChanged = true;
        }
    }

}