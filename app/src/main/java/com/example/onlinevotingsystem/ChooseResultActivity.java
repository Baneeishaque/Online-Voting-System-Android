package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseResultActivity extends AppCompatActivity {

    // TODO : Fix layout problems
    Context activityContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_result);

        if (getSupportActionBar() != null) getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.design_default_color_primary)));

        findViewById(R.id.button_results_assembly).setOnClickListener(v -> startActivity(new Intent(this, ChooseAssemblyResultsActivity.class)));


        findViewById(R.id.button_results_parliment).setOnClickListener(v -> startActivity(new Intent(this, ChooseParlimentResultsActivity.class)));
    }
}
