package com.example.onlinevotingsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class VoterOtpAuthenticationActivity extends AppCompatActivity {

    MaterialButton buttonEnterOtp;
    Context activityContext = this;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_otp_authentication);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO : Generate Otp, Send Otp, Store Otp
        buttonEnterOtp = findViewById(R.id.buttonEnterOtp);
        // TODO : Auto Read Otp
        // TODO : Match Otp, if valid forward; otherwise error message
        buttonEnterOtp.setOnClickListener(v -> startActivity(new Intent(activityContext, VoterVotingActivity.class)));
    }
}
