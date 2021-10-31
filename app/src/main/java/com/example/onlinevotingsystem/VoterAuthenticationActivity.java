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

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class VoterAuthenticationActivity extends AppCompatActivity {

    EditText editTextVoterId, editTextPhoneNumber;
    MaterialButton buttonGetOtp;

    Context activityContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_authentication);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.design_default_color_primary)));
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

                ProgressBar progressBar = findViewById(R.id.progressBar);
                ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

                SendOtpNetworkTask.showProgress(true, activityContext, progressBar, constraintLayout);
                new GetVoterNetworkTask(editTextVoterId.getText().toString().trim(), activityContext, progressBar, constraintLayout, response -> {

//                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
                    Log.d(ApplicationSpecification.name, "Response : " + response);

//                    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor=sharedPreferences.edit();
//                    editor.putString("response",response);
//                    editor.apply();

                    if (response.equals("null")) {

                        Toast.makeText(getApplicationContext(), "Unrecognised Voter!", Toast.LENGTH_LONG).show();

                    } else {

                        try {

                            JSONObject voterJsonObject = new JSONObject(response);
//                            {
//                                "aadharNumber":"67",
//                                "address":"V5j",
//                                "age":"67",
//                                "assemblyName":"Malappuram",
//                                "assemblyVoteDone":false,
//                                "dob":"APR 12 2021",
//                                "gender":"Male",
//                                "mobileNumber":"9446827218",
//                                "name":"V5",
//                                "parliamentName":"Malappuram",
//                                "parlimentVoteDone":false,
//                                "voterId":"v3"
//                            }

//                            editor.putString("voterJSONObject", voterJsonObject.toString());
//                            editor.apply();

                            Log.d(ApplicationSpecification.name,"Voter JSON Object : "+ voterJsonObject);

                            String voterMobile = voterJsonObject.getString("mobileNumber");
                            if (voterMobile.equals(editTextPhoneNumber.getText().toString().trim())) {

                                Toast.makeText(getApplicationContext(), "Authentication Success!", Toast.LENGTH_LONG).show();

                                Random rnd = new Random();
                                int otp = 100000 + rnd.nextInt(900000);
                                // Toast.makeText(getApplicationContext(), "Otp : " + otp, Toast.LENGTH_SHORT).show();
                                Log.d(ApplicationSpecification.name, "Otp : " + otp);

                                SendOtpNetworkTask.showProgress(true, activityContext, progressBar, constraintLayout);

                                //TODO :Check for SMS balance
                                new SendOtpNetworkTask(String.valueOf(otp), voterMobile, activityContext, progressBar, constraintLayout, jsonObject -> {

                                    try {
                                        if (jsonObject.getString("return").equals("true")) {

                                            Toast.makeText(getApplicationContext(), "Otp send success...", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(VoterAuthenticationActivity.this, VoterOtpAuthenticationActivity.class);

                                            intent.putExtra("otp", otp);
                                            intent.putExtra("voterMobile", voterMobile);
                                            intent.putExtra("response", response);
                                            intent.putExtra("voterJsonObject", voterJsonObject.toString());

                                            String assembly = voterJsonObject.getString("assemblyName");
//                                            editor.putString("assemblyName", assembly);
                                            String parliment = voterJsonObject.getString("parliamentName");
//                                            editor.putString("parliment", parliment);
                                            String voterId = voterJsonObject.getString("voterId");
//                                            editor.putString("voterId", voterId);
//                                            editor.apply();

                                            Log.d(ApplicationSpecification.name, "Assembly : " + assembly + ", Parliment : " + parliment + ", Voter : " + voterId);

                                            Toast.makeText(getApplicationContext(), "Assembly : " + assembly, Toast.LENGTH_LONG).show();
                                            Toast.makeText(getApplicationContext(), "Parliment : " + parliment, Toast.LENGTH_LONG).show();
                                            Toast.makeText(getApplicationContext(), "Voter ID : " + voterId, Toast.LENGTH_LONG).show();

                                            intent.putExtra("assembly", assembly);
                                            intent.putExtra("parliment", parliment);
                                            intent.putExtra("voterId", voterId);

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
                        } catch (JSONException e) {
                            Log.d(ApplicationSpecification.name, "Exception : " + e.getLocalizedMessage());
                        }
                    }
                }).execute();

//                DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
//                DatabaseReference votersNode = rootNode.child("voters");
//
//                votersNode.child(editTextVoterId.getText().toString().trim()).get().addOnCompleteListener(task -> {
//
//                    if (task.isSuccessful()) {
//
//                        DataSnapshot data = task.getResult();
//                        if (data != null && data.exists()) {
//
//                            VoterInfoModal voter = data.getValue(VoterInfoModal.class);
//
//                            Log.d(ApplicationSpecification.name, "Voter : " + voter);
//
//                            if (voter != null) {
//
//                                String voterMobile = voter.getMobileNumber();
//                                if (voterMobile.equals(editTextPhoneNumber.getText().toString())) {
//
//                                    Toast.makeText(getApplicationContext(), "Authentication Success!", Toast.LENGTH_LONG).show();
//
//                                    Random rnd = new Random();
//                                    int otp = 100000 + rnd.nextInt(900000);
//                                    // Toast.makeText(getApplicationContext(), "Otp : " + otp, Toast.LENGTH_SHORT).show();
//
//                                    ProgressBar progressBar = findViewById(R.id.progressBar);
//                                    ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
//
//                                    SendOtpNetworkTask.showProgress(true, this, progressBar, constraintLayout);
//
//                                    //TODO :Check for SMS balance
//                                    new SendOtpNetworkTask(String.valueOf(otp), voterMobile, this, progressBar, constraintLayout, jsonObject -> {
//
//                                        try {
//                                            if (jsonObject.getString("return").equals("true")) {
//
//                                                Toast.makeText(getApplicationContext(), "Otp send success...", Toast.LENGTH_SHORT).show();
//
//                                                Intent intent = new Intent(activityContext, VoterOtpAuthenticationActivity.class);
//
//                                                intent.putExtra("otp", otp);
//                                                intent.putExtra("voterMobile", voterMobile);
//
//                                                String assembly = voter.getAssemblyName();
//                                                String parliament = voter.getParliamentName();
//
//                                                Toast.makeText(activityContext, "Assembly : " + voter.assemblyName, Toast.LENGTH_LONG).show();
//                                                Toast.makeText(activityContext, "Parliment : " + voter.parliamentName, Toast.LENGTH_LONG).show();
//                                                Toast.makeText(activityContext, "Voter ID : " + voter.getVoterId(), Toast.LENGTH_LONG).show();
//
//                                                intent.putExtra("assembly", voter.assemblyName);
//                                                intent.putExtra("parliment", voter.parliamentName);
//                                                intent.putExtra("voterId", voter.getVoterId());
//
//                                                startActivity(intent);
//
//                                            } else {
//
//                                                Toast.makeText(getApplicationContext(), "Otp send failed, try again...", Toast.LENGTH_SHORT).show();
//                                            }
//                                        } catch (JSONException e) {
//
//                                            Toast.makeText(getApplicationContext(), "Error : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).execute();
//
//
//                                } else {
//
//                                    Toast.makeText(getApplicationContext(), "Invalid Credentials!", Toast.LENGTH_LONG).show();
//                                }
//
//                            } else {
//
//                                Toast.makeText(getApplicationContext(), "Unrecognised Voter!", Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//
//                            Log.e(ApplicationSpecification.name, "firebase : Error getting data", task.getException());
//                        }
//                    }
//                });
            }
        });
    }
}
