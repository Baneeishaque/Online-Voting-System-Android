package com.example.onlinevotingsystem;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // TODO : Override Back Press

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        findViewById(R.id.button_admin).setOnClickListener(v -> openAdminActivity());
        findViewById(R.id.button_voter).setOnClickListener(v -> openVoterActivity());
        findViewById(R.id.button_result).setOnClickListener(v -> openResultActivity());
    }

    public void openAdminActivity() {

        startActivity(new Intent(this, AdminAuthenticationActivity.class));
    }

    public void openVoterActivity() {

        startActivity(new Intent(this, VoterAuthenticationActivity.class));
    }

    public void openResultActivity() {

//        startActivity(new Intent(this, ParlimentResultActivity.class));
    }
}
