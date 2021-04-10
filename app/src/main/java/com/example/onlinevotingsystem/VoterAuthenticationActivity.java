package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

public class VoterAuthenticationActivity extends AppCompatActivity {

    EditText editTextVoterId, editTextPhoneNumber;
    MaterialButton buttonGetOtp;

    Context activityContext = this;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_authentication);

        getWindow().setStatusBarColor(ContextCompat.getColor(VoterAuthenticationActivity.this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonGetOtp = findViewById(R.id.buttonGetOtp);
        buttonGetOtp.setOnClickListener(v -> {

            //TODO : Empty check
            editTextVoterId = findViewById(R.id.editTextVoterId);
            editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

            DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
            DatabaseReference votersNode = rootNode.child("voters");

            votersNode.child(editTextVoterId.getText().toString().trim()).get().addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    DataSnapshot data = task.getResult();
                    if (data != null && data.exists()) {

                        VoterInfoModal voter = data.getValue(VoterInfoModal.class);

                        Log.d(ApplicationSpecification.name, "Voter : " + voter);

                        if (voter != null && voter.getMobileNumber().equals(editTextPhoneNumber.getText().toString())) {

                            Toast.makeText(getApplicationContext(), "Authentication Success!", Toast.LENGTH_LONG).show();
                            Intent intToAdmin = new Intent(activityContext, VoterOtpAuthenticationActivity.class);
                            startActivity(intToAdmin);

                        } else {

                            Toast.makeText(getApplicationContext(), "Invalid Credentials!", Toast.LENGTH_LONG).show();
                        }

                    } else {

                        Toast.makeText(getApplicationContext(), "Unrecognised Voter!", Toast.LENGTH_LONG).show();
                    }
                } else {

                    Log.e(ApplicationSpecification.name, "firebase : Error getting data", task.getException());
                }
            });
        });
    }
}
