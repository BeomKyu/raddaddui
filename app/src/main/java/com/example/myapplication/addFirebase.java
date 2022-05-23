package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicMarkableReference;

interface MyCallBack{
    void onCallback(List<Map<String, Object>> value);
}
interface MyOnceCallBack{
    void onCallback(List<Map<String, Object>> value);
}
interface MyPictureCallBack{
    void onCallback(byte[] bytes);
}

public class addFirebase {
    private static FirebaseAuth user_instance = FirebaseAuth.getInstance();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseStorage storage = FirebaseStorage.getInstance();
    static StorageReference storageRef = storage.getReference();
    static String TAG="MyTag";

    static String tempId;

    public static String add_new_ingredient(String food_type, String food_name,
                                            Timestamp expiration_date, String Id,
                                            Timestamp purchase_date,
                                            String storage_location, boolean imageChanged,
                                            ImageButton camera_btn){

        Map<String, Object> user = new HashMap<>();
        user.put("카테고리", food_type);
        user.put("상품명", food_name);
        user.put("유통기한", expiration_date);
        user.put("구매일자", purchase_date);
        user.put("보관위치", storage_location);
        user.put("Url", "false");

        if(Id == null) {
            db.collection("user").document(user_instance.getUid())
                    .collection("ingredient").add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            if(imageChanged)
                                add_picture(camera_btn, documentReference.getId());
                            Log.d("aaa", "Document Snapshot written with Id " + documentReference.getId());
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
        return tempId;
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
                    map.put("카테고리", doc.getString("카테고리"));
                    map.put("상품명", doc.getString("상품명"));
                    map.put("Id", doc.getId());
                    map.put("구매일자", doc.getDate("구매일자"));
                    map.put("보관위치", doc.getString("보관위치"));
                    map.put("Url", doc.getString("Url"));

                    Log.d(TAG, String.valueOf(doc.getData()));
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
                                map.put("카테고리", document.getString("카테고리"));
                                map.put("상품명", document.getString("상품명"));
                                map.put("Id", document.getId());
                                map.put("구매일자", document.getDate("구매일자"));
                                map.put("보관위치", document.getString("보관위치"));
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

    public static void add_picture(ImageView camera_btn, String id) {
        StorageReference storageRef = storage.getReference();
        Log.d(TAG, "id" + id);
// Create a reference to "mountains.jpg"
        StorageReference imageRef = storageRef.child(user_instance.getUid() + "/" + id + ".jpeg");
        camera_btn.setDrawingCacheEnabled(true);
        camera_btn.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) camera_btn.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Log.d(TAG, "fail");
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Map<String, Object> user = new HashMap<>();
                user.put("Url", "https://firebasestorage.googleapis.com/v0/b/rada-b8814.appspot.com/o/"
                        + user_instance.getUid() + "%2F" +
                        id + ".jpeg?alt=media");
                db.collection("user").document(user_instance.getUid())
                        .collection("ingredient").document(id)
                        .set(user, SetOptions.merge());
                Log.d(TAG, "success");
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
        // [END upload_memory]
    }
    public static void load_picture(String id, MyPictureCallBack myPictureCallBack){
        StorageReference pathReference = storageRef.child(user_instance.getUid() + "/" + id + ".jpeg");

        pathReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                Log.d("Tag", "success");
                myPictureCallBack.onCallback(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Tag", "fail");
                myPictureCallBack.onCallback(null);
                // Handle any errors
            }
        });
    }
}
