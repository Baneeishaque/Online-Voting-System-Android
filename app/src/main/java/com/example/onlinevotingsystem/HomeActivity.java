package com.example.onlinevotingsystem;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    MaterialButton btnLogout;
    MaterialButton createCandidate;
    MaterialButton createVoter;
    MaterialButton viewResults;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        btnLogout = findViewById(R.id.Logout);
        btnLogout.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();
            Intent intToAdmin = new Intent(HomeActivity.this, AdminActivity.class);
            startActivity(intToAdmin);
        });

        createCandidate = findViewById(R.id.Candidate);
        createCandidate.setOnClickListener(v -> {

            Intent intent = new Intent(HomeActivity.this, CreateCandidateActivity.class);
            startActivity(intent);
        });

        createVoter = findViewById(R.id.Voter);
        createVoter.setOnClickListener(v -> {

            Intent intent = new Intent(HomeActivity.this, CVoterActivity.class);
            startActivity(intent);
        });

        viewResults = findViewById(R.id.Results);
        viewResults.setOnClickListener(v -> {

            Intent intent = new Intent(HomeActivity.this, ResultActivity.class);
            startActivity(intent);
        });
    }
}
