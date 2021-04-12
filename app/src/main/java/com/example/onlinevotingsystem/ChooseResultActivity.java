package com.example.onlinevotingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChooseResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_result);

        findViewById(R.id.button_results_assembly).setOnClickListener(v -> {

            startActivity(new Intent(this, ChooseAssemblyResultsActivity.class));
        });


        findViewById(R.id.button_results_parliment).setOnClickListener(v -> {

            startActivity(new Intent(this, ChooseParlimentResultsActivity.class));
        });
    }
}