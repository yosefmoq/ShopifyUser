package com.app.shopifyuser.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.shopifyuser.R;
import com.app.shopifyuser.driver.DriverActivity;
import com.app.shopifyuser.shared.LocalSave;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (LocalSave.getInstance(this).getCurrentUser() != null) {

            if (LocalSave.getInstance(this).getCurrentUser().getType() == 1) {
                startActivity(new Intent(this, UserActivity.class));
            } else {
                startActivity(new Intent(this, DriverActivity.class));
            }

        } else {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, 3000);

        }


//        new CountDownTimer(3000,1000){
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();
    }
}