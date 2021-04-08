package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class VoterActivity extends AppCompatActivity {

    MaterialButton buttonGetOtp;
    Context activityContext = this;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter);

        getWindow().setStatusBarColor(ContextCompat.getColor(VoterActivity.this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonGetOtp = findViewById(R.id.buttonGetOtp);
        buttonGetOtp.setOnClickListener(v -> {

            // TODO : Get Voter Data from DB, if valid go; otherwise error message
            Intent intToAdmin = new Intent(activityContext, VoterOtpActivity.class);
            startActivity(intToAdmin);
        });
    }
}