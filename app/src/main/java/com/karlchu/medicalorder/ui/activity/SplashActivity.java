package com.karlchu.medicalorder.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.karlchu.medicalorder.core.A;


public class SplashActivity extends AppCompatActivity {
    private final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = null;

        intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
        finish();
    }
}
