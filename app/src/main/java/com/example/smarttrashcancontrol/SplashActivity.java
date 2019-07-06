package com.example.smarttrashcancontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH = 2000;

    ImageView logo;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo);
        background = findViewById(R.id.background);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginClass();
            }
        },SPLASH);
    }
    private void loginClass(){
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
