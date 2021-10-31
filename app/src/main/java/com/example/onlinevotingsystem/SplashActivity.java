package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

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

                    Log.d(ApplicationSpecification.name, "Exception : " + e);

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
