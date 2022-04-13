package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class addFirebase {
    private static FirebaseAuth user_instance = FirebaseAuth.getInstance();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void add_new_ingredient(String food_name, Timestamp time){
        DocumentReference users_ingredient = db.collection("users").document(user_instance.getUid());
        final String TAG="MyTag";
        Map<String, Object> user = new HashMap<>();
        user.put("재료명", food_name);
        user.put("유통기한", time);

        db.collection("user").document(user_instance.getUid())
                .collection("ingredient").add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Document Snapshot written with Id");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error adding document", e);
            }
        });
    }
}
