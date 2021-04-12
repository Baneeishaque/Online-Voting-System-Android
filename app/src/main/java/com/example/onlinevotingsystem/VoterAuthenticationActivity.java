package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.util.Random;

public class VoterAuthenticationActivity extends AppCompatActivity {

    EditText editTextVoterId, editTextPhoneNumber;
    MaterialButton buttonGetOtp;

    Context activityContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_authentication);

        getWindow().setStatusBarColor(ContextCompat.getColor(VoterAuthenticationActivity.this, R.color.colorAccent));
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonGetOtp = findViewById(R.id.buttonGetOtp);
        buttonGetOtp.setOnClickListener(v -> {

            editTextVoterId = findViewById(R.id.editTextVoterId);
            editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

            if (editTextVoterId.getText().toString().isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please enter Voter ID...", Toast.LENGTH_SHORT).show();
                editTextVoterId.setError("Please enter Voter ID...");
                editTextVoterId.requestFocus();

            } else if (editTextPhoneNumber.getText().toString().isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please enter Mobile Number...", Toast.LENGTH_SHORT).show();
                editTextPhoneNumber.setError("Please enter Phone Number...");
                editTextPhoneNumber.requestFocus();

            } else {

                DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
                DatabaseReference votersNode = rootNode.child("voters");

                votersNode.child(editTextVoterId.getText().toString().trim()).get().addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        DataSnapshot data = task.getResult();
                        if (data != null && data.exists()) {

                            VoterInfoModal voter = data.getValue(VoterInfoModal.class);

                            Log.d(ApplicationSpecification.name, "Voter : " + voter);

                            if (voter != null) {

                                String voterMobile = voter.getMobileNumber();
                                if (voterMobile.equals(editTextPhoneNumber.getText().toString())) {

                                    Toast.makeText(getApplicationContext(), "Authentication Success!", Toast.LENGTH_LONG).show();

                                    Random rnd = new Random();
                                    int otp = 100000 + rnd.nextInt(900000);
                                    Toast.makeText(getApplicationContext(), "Otp : " + otp, Toast.LENGTH_SHORT).show();

                                    ProgressBar progressBar = findViewById(R.id.progressBar);
                                    ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

                                    SendOtpNetworkTask.showProgress(true,this,progressBar,constraintLayout);

                                    //TODO :Check for SMS balance
                                    new SendOtpNetworkTask(String.valueOf(otp), voterMobile, this, progressBar, constraintLayout, jsonObject -> {

                                        try {
                                            if (jsonObject.getString("return").equals("true")) {

                                                Toast.makeText(getApplicationContext(), "Otp send success...", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(activityContext, VoterOtpAuthenticationActivity.class);

                                                intent.putExtra("otp", otp);
                                                intent.putExtra("voterMobile", voterMobile);
                                                intent.putExtra("assembly",voter.getAssemblyName());
                                                intent.putExtra("parliment",voter.getParliamentName());
                                                intent.putExtra("voterId",voter.getVoterId());

                                                startActivity(intent);

                                            } else {

                                                Toast.makeText(getApplicationContext(), "Otp send failed, try again...", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {

                                            Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).execute();


                                } else {

                                    Toast.makeText(getApplicationContext(), "Invalid Credentials!", Toast.LENGTH_LONG).show();
                                }

                            } else {

                                Toast.makeText(getApplicationContext(), "Unrecognised Voter!", Toast.LENGTH_LONG).show();
                            }
                        } else {

                            Log.e(ApplicationSpecification.name, "firebase : Error getting data", task.getException());
                        }
                    }
                });
            }
        });
    }
}
