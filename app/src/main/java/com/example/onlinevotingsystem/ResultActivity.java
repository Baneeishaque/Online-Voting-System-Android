package com.example.onlinevotingsystem;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public RecyclerView recyclerView;

    ArrayList<ResultModal> results = new ArrayList<>();
    private final ResultsRecyclerViewAdaptor resultsRecyclerViewAdaptor = new ResultsRecyclerViewAdaptor(results);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getWindow().setStatusBarColor(ContextCompat.getColor(ResultActivity.this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);

        ResultModal result1 = new ResultModal(ContextCompat.getDrawable(this, R.drawable.udf), "UDF", 45);
        ResultModal result2 = new ResultModal(ContextCompat.getDrawable(this, R.drawable.ldf), "LDF", 40);
        ResultModal result3 = new ResultModal(ContextCompat.getDrawable(this, R.drawable.nda), "NDA", 5);

        results.add(result1);
        results.add(result2);
        results.add(result3);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(resultsRecyclerViewAdaptor);

        resultsRecyclerViewAdaptor.SetOnItemClickListener((view, position, candidate) -> {

            //handle item click events here
        });
    }
}