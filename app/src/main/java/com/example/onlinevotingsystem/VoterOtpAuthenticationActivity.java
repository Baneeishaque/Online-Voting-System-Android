package com.example.onlinevotingsystem;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mukesh.OtpView;

import org.json.JSONException;

import java.util.Random;

public class VoterOtpAuthenticationActivity extends AppCompatActivity {

    int otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_otp_authentication);

        otp = getIntent().getIntExtra("otp", 0);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.design_default_color_primary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO : Auto Read Otp
        findViewById(R.id.buttonEnterOtp).setOnClickListener(v -> {

            OtpView otpView = findViewById(R.id.otpView);
            if (otpView.getText() != null) {
                if (Integer.parseInt(otpView.getText().toString()) == otp) {

                    Toast.makeText(getApplicationContext(), "Otp authentication success", Toast.LENGTH_LONG).show();

//                    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//                    Toast.makeText(activityContext, "Assembly : " + sharedPreferences.getString("assembly", ""), Toast.LENGTH_LONG).show();
//                    Toast.makeText(activityContext, "Parliment : " + sharedPreferences.getString("parliment", ""), Toast.LENGTH_LONG).show();
//                    Toast.makeText(activityContext, "Voter ID : " + sharedPreferences.getString("voterId", ""), Toast.LENGTH_LONG).show();
//                    Toast.makeText(activityContext, "Response : " + sharedPreferences.getString("response", ""), Toast.LENGTH_LONG).show();
//                    Toast.makeText(activityContext, "Voter JSON Object : " + sharedPreferences.getString("voterJsonObject", ""), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(VoterOtpAuthenticationActivity.this, VoterChooseVoteActivity.class);

                    String assembly = getIntent().getStringExtra("assembly");
                    String parliment = getIntent().getStringExtra("parliment");
                    String voterId = getIntent().getStringExtra("voterId");
                    String response = getIntent().getStringExtra("response");
                    String voterJsonObjectString = getIntent().getStringExtra("voterJsonObject");

                    Log.d(ApplicationSpecification.name, "Assembly : " + assembly + ", Parliment : " + parliment + ", Voter : " + voterId + ", Response : " + response + ", Voter JSON Object : " + voterJsonObjectString);

                    Toast.makeText(getApplicationContext(), "Assembly : " + assembly, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Parliment : " + parliment, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Voter ID : " + voterId, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Voter JSON Object : " + voterJsonObjectString, Toast.LENGTH_LONG).show();

                    intent.putExtra("assembly", assembly);
                    intent.putExtra("parliment", parliment);
                    intent.putExtra("voterId", voterId);

                    startActivity(intent);

                } else {

                    Toast.makeText(getApplicationContext(), "Otp authentication failure, try again...", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.buttonRegenerateOtp).setOnClickListener(v -> {

            otp = new Random().nextInt(((999999 + 1) - 100000) + 1) + 100000;
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
