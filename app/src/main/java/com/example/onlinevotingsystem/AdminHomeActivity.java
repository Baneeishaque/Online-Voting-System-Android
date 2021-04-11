package com.example.onlinevotingsystem;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity {

    // TODO : Change username & password of admin
    // TODO : Forgot password for admin
    // TODO : Manage other admins
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getWindow().setStatusBarColor(ContextCompat.getColor(AdminHomeActivity.this, R.color.colorAccent));
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        findViewById(R.id.button_logout).setOnClickListener(v -> {

            // TODO : Evaluate Returns for firebase actions
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminHomeActivity.this, AdminAuthenticationActivity.class));
        });

        findViewById(R.id.button_create_assembly_candidate).setOnClickListener(v -> {

            startActivity(new Intent(AdminHomeActivity.this, CreateAssemblyCandidateActivity.class));
        });

        findViewById(R.id.button_create_parliment_candidate).setOnClickListener(v -> {

             startActivity(new Intent(AdminHomeActivity.this, CreateParlimentCandidateActivity.class));
        });

        findViewById(R.id.button_create_voter).setOnClickListener(v -> {

            startActivity(new Intent(AdminHomeActivity.this, CreateVoterActivity.class));
        });

        findViewById(R.id.button_view_assembly_results).setOnClickListener(v -> {

            startActivity(new Intent(AdminHomeActivity.this, ResultActivity.class));
        });
        
        findViewById(R.id.button_view_parliment_results).setOnClickListener(v -> {

            // startActivity(new Intent(AdminHomeActivity.this, ResultActivity.class));
        });
    }
}
