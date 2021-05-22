package com.app.shopifyuser.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.shopifyuser.R;
import com.app.shopifyuser.adapters.MenuAdapter;
import com.app.shopifyuser.model.Menu;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderActivity extends AppCompatActivity {


    ImageView ivImage;
    TextView tvTitle, tvDescription, tvNumber;
    ImageButton ibAdd, ibRemove;
    Menu menu;

    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    MenuAdapter menuAdapter;
    SweetAlertDialog sweetAlertDialog;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initViews();
        initItems();
        initClicks();
    }

    private void initClicks() {
        ibAdd.setOnClickListener(v -> {
            quantity += 1;
            tvNumber.setText(quantity + "");
        });
        ibRemove.setOnClickListener(v -> {
            if (quantity != 1) {
                quantity -= 1;
                tvNumber.setText(quantity + "");

            }
        });
    }

    private void initItems() {
        menu =MenuActivity.menus.get(getIntent().getIntExtra("position",0));
        Picasso.get().load(menu.getImage());
        tvTitle.setText(menu.getName());
        tvDescription.setText(menu.getDescription());
    }

    private void initViews() {
        ivImage = findViewById(R.id.ivImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDesc);
        tvNumber = findViewById(R.id.tvNumber);
        ibAdd = findViewById(R.id.ibPlus);
        ibRemove = findViewById(R.id.ibRemove);
    }
}