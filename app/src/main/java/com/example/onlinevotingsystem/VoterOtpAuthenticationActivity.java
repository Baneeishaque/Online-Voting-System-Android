package com.example.onlinevotingsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mukesh.OtpView;

import org.json.JSONException;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class VoterOtpAuthenticationActivity extends AppCompatActivity {

    int otp;
    Context activityContext = this;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_otp_authentication);

        otp = getIntent().getIntExtra("otp", 0);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO : Auto Read Otp
        findViewById(R.id.buttonEnterOtp).setOnClickListener(v -> {

            OtpView otpView = findViewById(R.id.otpView);
            if (otpView.getText() != null) {
                if (Integer.parseInt(otpView.getText().toString()) == otp) {

                    Toast.makeText(getApplicationContext(), "Otp authentication success", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(activityContext, VoterVotingActivity.class);

                    Toast.makeText(activityContext, "Assembly : "+getIntent().getStringExtra("assembly"),Toast.LENGTH_LONG).show();
                    Toast.makeText(activityContext, "Parliment : "+getIntent().getStringExtra("parliment"),Toast.LENGTH_LONG).show();
                    Toast.makeText(activityContext, "Voter ID : "+getIntent().getStringExtra("voterId"),Toast.LENGTH_LONG).show();

                    intent.putExtra("assembly", getIntent().getStringExtra("assembly"));
                    intent.putExtra("parliment", getIntent().getStringExtra("parliment"));
                    intent.putExtra("voterId",getIntent().getStringExtra("voterId"));
                    
                    startActivity(intent);

                } else {

                    Toast.makeText(getApplicationContext(), "Otp authentication failure, try again...", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.buttonRegenerateOtp).setOnClickListener(v -> {

            otp = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
            Toast.makeText(getApplicationContext(), "Otp : " + otp, Toast.LENGTH_SHORT).show();

            ProgressBar progressBar = findViewById(R.id.progressBar);
            ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

            SendOtpNetworkTask.showProgress(true, this, progressBar, constraintLayout);

            new SendOtpNetworkTask(String.valueOf(otp), getIntent().getStringExtra("voterMobile"), this, progressBar, constraintLayout, jsonObject -> {

                try {
                    if (jsonObject.getString("return").equals("true")) {

                        Toast.makeText(getApplicationContext(), "Otp send success...", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getApplicationContext(), "Otp send failed, try again...", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                    Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }).execute();
        });
    }
}
