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
        Button Signoutbtn = (Button) findViewById(R.id.signout_btn);
        mAuth = FirebaseAuth.getInstance();

        Revokebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFirebase.delete_user_all_document();
//                revokeAccess();
//                addFirebase.delete_user_document();
//                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        Signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
//                finish();
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