package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChooseParlimentResultsActivity extends AppCompatActivity {

    // TODO : Fix layout problems
    Context activityContext = this;

    ArrayList<String> arrayList_parliament;
    ArrayAdapter<String> arrayAdapter_parliament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_parliment);

        if (getSupportActionBar() != null) getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.design_default_color_primary)));



        arrayList_parliament = new ArrayList<>();
        arrayList_parliament.add("Malappuram");
        arrayList_parliament.add("Ponnani");
        arrayList_parliament.add("Wayanad");

        arrayAdapter_parliament = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_parliament);

        Spinner spinnerParliment = findViewById(R.id.spinner_parliment);
        spinnerParliment.setAdapter(arrayAdapter_parliament);

        findViewById(R.id.button_submit).setOnClickListener(v -> {

            Intent intent = new Intent(activityContext, ParlimentResultActivity.class);
            intent.putExtra("parliment", spinnerParliment.getSelectedItem().toString());
            startActivity(intent);
        });
    }
}
