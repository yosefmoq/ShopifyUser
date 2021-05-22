package com.app.shopifyuser.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.shopifyuser.R;
import com.app.shopifyuser.adapters.ResturantAdapter;
import com.app.shopifyuser.model.Resturant;
import com.app.shopifyuser.shared.LocalSave;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    SweetAlertDialog sweetAlertDialog;
    RecyclerView rvRes;
    ArrayList<Resturant> resturants = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    ResturantAdapter resturantAdapter;

    private Toolbar mainToolbar;
    private DrawerLayout drawer_layout;
    private NavigationView navigationView;

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

        mainToolbar.setNavigationOnClickListener(v -> showDrawer());
        mainToolbar.setOnMenuItemClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        collectionReference.get().addOnCompleteListener(command -> {
            resturants.clear();
            if (command.isSuccessful()) {
                for (DocumentSnapshot d : command.getResult().getDocuments()) {
                    resturants.add(d.toObject(Resturant.class));
                }
                resturantAdapter.notifyDataSetChanged();
                sweetAlertDialog.hide();
            }
        });
    }

    public void showDrawer() {
        drawer_layout.openDrawer(GravityCompat.START);
    }

    private void initItems() {
        sweetAlertDialog = new SweetAlertDialog(UserActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setCancelable(false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("resturants");
        resturantAdapter = new ResturantAdapter(UserActivity.this, resturants);
        rvRes.setLayoutManager(new LinearLayoutManager(UserActivity.this));
        rvRes.setAdapter(resturantAdapter);


    }

    private void initViews() {
        mainToolbar = findViewById(R.id.mainToolbar);
        rvRes = findViewById(R.id.rvRes);

        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getItemId() == R.id.showCart) {
            startActivity(new Intent(UserActivity.this, CartActivity.class));
        } else if (item.getItemId() == R.id.activeOrders) {
            startActivity(new Intent(UserActivity.this, ActiveOrdersAcitivity.class));
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.signOut) {

            LocalSave.getInstance(this).clear();
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }

        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}