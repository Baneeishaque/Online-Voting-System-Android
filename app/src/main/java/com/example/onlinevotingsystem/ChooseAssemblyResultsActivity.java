package com.example.onlinevotingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ChooseAssemblyResultsActivity extends AppCompatActivity {

    // TODO : Fix layout problems
    Context activityContext = this;

    ArrayList<String> arrayList_malappuram, arrayList_ponnani, arrayList_wayanad;
    ArrayAdapter<String> arrayAdapter_assembly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_assembly);

        getWindow().setStatusBarColor(ContextCompat.getColor(activityContext, R.color.colorAccent));
        if (getSupportActionBar() != null) getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));



        arrayList_malappuram = new ArrayList<>();
        arrayList_malappuram.add("Malappuram");
        arrayList_malappuram.add("Manjeri");
        arrayList_malappuram.add("Mankada");
        arrayList_malappuram.add("Perinthalmanna");
        arrayList_malappuram.add("Vallikunnu");
        arrayList_malappuram.add("Kondotty");
        arrayList_malappuram.add("Vengara");

        arrayList_ponnani = new ArrayList<>();
        arrayList_ponnani.add("Ponnani");
        arrayList_ponnani.add("Tirur");
        arrayList_ponnani.add("Tanur");
        arrayList_ponnani.add("Tirurangadi");
        arrayList_ponnani.add("Kottakkal");
        arrayList_ponnani.add("Thavanur");

        arrayList_wayanad = new ArrayList<>();
        arrayList_wayanad.add("Eranad");
        arrayList_wayanad.add("Wandoor");
        arrayList_wayanad.add("Nilambur");

        ArrayList<String> arrayList_assemblies = new ArrayList<>();
        arrayList_assemblies.addAll(arrayList_malappuram);
        arrayList_assemblies.addAll(arrayList_ponnani);
        arrayList_assemblies.addAll(arrayList_wayanad);

        arrayAdapter_assembly = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_assemblies);

        Spinner spinnerAssembly = findViewById(R.id.spinner_assembly);
        spinnerAssembly.setAdapter(arrayAdapter_assembly);

        findViewById(R.id.button_submit).setOnClickListener(v -> {

            Intent intent = new Intent(activityContext, AssemblyResultActivity.class);
            intent.putExtra("assembly", spinnerAssembly.getSelectedItem().toString());
            startActivity(intent);
        });
    }
}
