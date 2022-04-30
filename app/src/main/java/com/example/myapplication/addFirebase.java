package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
interface MyOnceCallBack{
    void onCallback(List<Map<String, Object>> value);
}
public class addFirebase {
    private static FirebaseAuth user_instance = FirebaseAuth.getInstance();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String TAG="MyTag";

    public static void add_new_ingredient(String food_type, String food_name, Timestamp time, String Id){
        Map<String, Object> user = new HashMap<>();
        user.put("재료명", food_type);
        user.put("상품명", food_name);
        user.put("유통기한", time);
        if(Id == null) {
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
        else{
            db.collection("user").document(user_instance.getUid())
                    .collection("ingredient").document(Id).set(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    public static void listen_document_multiple(MyOnceCallBack myCallBack){
//        List<Map> ingredient_map = new ArrayList<>();
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

    public static void listen_document_multiple_once(MyOnceCallBack myOnceCallBack){
//        List<Map<String, Object>> list = new ArrayList<>();
        db.collection("user").document(user_instance.getUid())
                .collection("ingredient")
                .orderBy("유통기한", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<Map<String, Object>> list = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Map<String, Object> map = new HashMap<>();
                                map.put("유통기한", document.getDate("유통기한"));
                                map.put("재료명", document.getString("재료명"));
                                map.put("상품명", document.getString("상품명"));
                                map.put("Id", document.getId());
//                                Log.d(TAG, String.valueOf(document.getData()));
                                list.add(map);
                            }
                            myOnceCallBack.onCallback(list);
                        }
                        else{
                            myOnceCallBack.onCallback(null);
                        }
                    }
                });
    }

    public static void delete_user_all_document(){

        db.collection("user")
                .document(user_instance.getUid())
                .collection("ingredient")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                delete_one_ingredient_document(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("user")
                .document(user_instance.getUid())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    public static void delete_one_ingredient_document(String Id){
        db.collection("user")
                .document(user_instance.getUid())
                .collection("ingredient")
                .document(Id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
