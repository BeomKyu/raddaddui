package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IngredientActivity extends AppCompatActivity {

    TextView Ingredient_Text;
    TextView timeStamp_Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        Button Registerbtn = (Button) findViewById(R.id.Registerbtn);
        Ingredient_Text = (TextView) findViewById(R.id.Ingredient_text);
        timeStamp_Text = (TextView) findViewById(R.id.Expirationdate_text);

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFirebase.add_new_ingredient(Ingredient_Text.getText().toString(),
                        timeStamp_Text.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MyFridge.class);
                startActivity(intent);
            }
        });
    }
}