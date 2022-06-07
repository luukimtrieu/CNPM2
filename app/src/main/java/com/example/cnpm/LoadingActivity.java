package com.example.cnpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        CountDownTimer countDownTimer = new CountDownTimer(3000,1) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(getIntent().getExtras() != null)
                {
                    if(getIntent().getExtras().getBoolean("new_account")){
                        Intent intent = new Intent(LoadingActivity.this, FirstAccessActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Intent intent = new Intent(LoadingActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        countDownTimer.start();
    }
}