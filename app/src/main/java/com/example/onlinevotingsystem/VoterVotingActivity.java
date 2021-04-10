package com.example.onlinevotingsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class VoterVotingActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public RecyclerView recyclerView;

    ArrayList<CandidateModal> candidates = new ArrayList<>();
    private final CandidatesRecyclerViewAdaptor candidatesRecyclerViewAdaptor = new CandidatesRecyclerViewAdaptor(candidates);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_voting);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);

        CandidateModal candidate1 = new CandidateModal(ContextCompat.getDrawable(this, R.drawable.udf), "UDF");
        CandidateModal candidate2 = new CandidateModal(ContextCompat.getDrawable(this, R.drawable.ldf), "LDF");

        candidates.add(candidate1);
        candidates.add(candidate2);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(candidatesRecyclerViewAdaptor);

        candidatesRecyclerViewAdaptor.SetOnItemClickListener((view, position, candidate) -> {

            //handle item click events here
            Toast.makeText(getApplicationContext(), "Selected : " + candidate.getName(), Toast.LENGTH_LONG).show();
        });
    }
}
