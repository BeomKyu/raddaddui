package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
interface MyCallBack{
    void onCallback(List<Map<String, Object>> value);
}
public class addFirebase {
    private static FirebaseAuth user_instance = FirebaseAuth.getInstance();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String TAG="MyTag";

    public static void add_new_ingredient(String food_type, String food_name, Timestamp time){
        DocumentReference users_ingredient = db.collection("users").document(user_instance.getUid());

        Map<String, Object> user = new HashMap<>();
        user.put("재료명", food_type);
        user.put("상품명", food_name);
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

    public static void read_document_multiple(MyCallBack myCallBack){

    }
    public static void listen_document_multiple(MyCallBack myCallBack){
        List<Map> ingredient_map = new ArrayList<>();
        db.collection("user").document(user_instance.getUid())
                .collection("ingredient")
                .orderBy("유통기한", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG, "listen failed", e);
                    return ;
                }
                List<Map<String, Object>> list = new ArrayList<>();
                for(QueryDocumentSnapshot doc : value){
                    Map<String, Object> map = new HashMap<>();
                    map.put("유통기한", doc.getDate("유통기한"));
                    map.put("재료명", doc.getString("재료명"));
                    map.put("상품명", doc.getString("상품명"));
                    map.put("Id", doc.getId());
                    //Log.d(TAG, String.valueOf(doc.getData()));
                    list.add(map);
                }
                myCallBack.onCallback(list);
            }
        });
    }

    public void listen_document_local(){
        final DocumentReference docRef = db.collection("user").document(user_instance.getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.w(TAG, "listen failed", e);
                    return ;
                }

                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites() ? "Local" : "Server";

                if(snapshot != null && snapshot.exists()){
                    Log.d(TAG, source + " data : "+ snapshot.getData());
                }else{
                    Log.d(TAG, source + " data : null");
                }
            }
        });
    }
}
