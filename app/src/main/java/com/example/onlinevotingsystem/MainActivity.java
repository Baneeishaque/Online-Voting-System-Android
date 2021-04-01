package com.example.onlinevotingsystem;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        Button admin = findViewById(R.id.admin);
        admin.setOnClickListener(v -> openAdminActivity());
        Button voter = findViewById(R.id.voter);
        voter.setOnClickListener(v -> openVoterActivity());
        Button result = findViewById(R.id.result);
        result.setOnClickListener(v -> openResultActivity());
    }

    public void openAdminActivity() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    public void openVoterActivity() {
        Intent intent = new Intent(this, VoterActivity.class);
        startActivity(intent);
    }

    public void openResultActivity() {
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
}