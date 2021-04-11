package com.example.onlinevotingsystem;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChooseParlimentActivity extends AppCompatActivity {

    // TODO : Fix layout problems
    ArrayList<String> arrayList_parliament;
    ArrayAdapter<String> arrayAdapter_parliament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_parliment);

        arrayList_parliament = new ArrayList<>();
        arrayList_parliament.add("Malappuram");
        arrayList_parliament.add("Ponnani");
        arrayList_parliament.add("Wayanad");

        arrayAdapter_parliament = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_parliament);
        ((Spinner)findViewById(R.id.spinner_parliment)).setAdapter(arrayAdapter_parliament);

        findViewById(R.id.button_submit).setOnClickListener(v -> {

            // startActivity(new Intent(activityContext, AdminAuthenticationActivity.class));
        });
    }
}
