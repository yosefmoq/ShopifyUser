package com.app.shopifyuser.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.shopifyuser.R;
import com.app.shopifyuser.adapters.ResturantAdapter;
import com.app.shopifyuser.model.Resturant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserActivity extends AppCompatActivity {

    SweetAlertDialog sweetAlertDialog;
    RecyclerView rvRes;
    ArrayList<Resturant> resturants = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    ResturantAdapter resturantAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initViews();
        initItems();
        initClicks();

    }

    private void initClicks() {
        sweetAlertDialog.show();
        collectionReference.get().addOnCompleteListener(command -> {
            resturants.clear();
            if(command.isSuccessful()){
                for(DocumentSnapshot d:command.getResult().getDocuments()){
                    resturants.add(d.toObject(Resturant.class));
                }
                resturantAdapter.notifyDataSetChanged();
                sweetAlertDialog.hide();
            }
        });
    }

    private void initItems() {
        sweetAlertDialog = new SweetAlertDialog(UserActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setCancelable(false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("resturants");
        resturantAdapter = new ResturantAdapter(UserActivity.this,resturants);
        rvRes.setLayoutManager(new LinearLayoutManager(UserActivity.this));
        rvRes.setAdapter(resturantAdapter);
    }

    private void initViews() {
        rvRes = findViewById(R.id.rvRes);

    }
}