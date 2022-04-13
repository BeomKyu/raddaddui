package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IngredientActivity extends AppCompatActivity {

    TextView Ingredient_Text;
    TextView timeStamp_Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        Button Registerbtn = (Button) findViewById(R.id.Registerbtn);
        Button Date_picker = (Button) findViewById(R.id.Date_picker);
        Ingredient_Text = (TextView) findViewById(R.id.Ingredient_text);
        timeStamp_Text = (TextView) findViewById(R.id.Expirationdate_text);
        timeStamp_Text.setText(getDate());

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try {
                    date = dateFormat.parse(timeStamp_Text.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long time = date.getTime();
                Timestamp ts = new Timestamp(time);
                addFirebase.add_new_ingredient(Ingredient_Text.getText().toString(), ts);
                Intent intent = new Intent(getApplicationContext(), MyFridge.class);
                startActivity(intent);
            }
        });

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                timeStamp_Text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, 2022, 1, 1);

        Date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
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
}