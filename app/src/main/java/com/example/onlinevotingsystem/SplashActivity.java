package com.example.onlinevotingsystem;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    Context activityContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (getSupportActionBar() != null) getSupportActionBar().hide();

        Thread thread = new Thread() {

            public void run() {

                try {

                    sleep(4000);

                } catch (Exception e) {

                    Log.d(ApplicationSpecification.name, "Exception : " + e.toString());

                } finally {

                    startActivity(new Intent(activityContext, MainActivity.class));
                    // TODO : Finish Activity
                    // TODO : Override Back Press
                }
            }
        };
        thread.start();
    }
}
