package com.example.onlinevotingsystem;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // TODO : Override Back Press

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.design_default_color_primary)));

        progressBar = findViewById(R.id.progressBar);

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

        AdminHomeActivity.checkResultTime(MainActivity.this, progressBar, getApplicationContext(), () -> startActivity(new Intent(MainActivity.this, ChooseResultActivity.class)));
    }
}
