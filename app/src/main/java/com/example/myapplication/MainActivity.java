package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button MyFridgebtn = (Button) findViewById(R.id.MyFridgebtn);
        Button Ingredientbtn = (Button) findViewById(R.id.Ingredientbtn);
        Button Recipebtn = (Button) findViewById(R.id.Recipebtn);
        Button Userbtn = (Button) findViewById(R.id.Userbtn);

        mAuth = FirebaseAuth.getInstance();

        MyFridgebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyFridge.class);
                startActivity(intent);
            }
        });

        Ingredientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IngredientActivity.class);
                startActivity(intent);
            }
        });

        Recipebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                startActivity(intent);
            }
        });

        Userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });

    }
    private void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
    private void revokeAccess(){
        mAuth.getCurrentUser().delete();
    }
}