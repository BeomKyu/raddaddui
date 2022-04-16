package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button Revokebtn = (Button) findViewById(R.id.revoke_btn);

        Revokebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revokeAccess();
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void revokeAccess(){
        mAuth.getCurrentUser().delete();
    }
}