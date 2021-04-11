package com.example.onlinevotingsystem;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class AssemblyResultActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public RecyclerView recyclerView;

    ArrayList<ResultModal> results = new ArrayList<>();
    private final ResultsRecyclerViewAdaptor resultsRecyclerViewAdaptor = new ResultsRecyclerViewAdaptor(results);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getWindow().setStatusBarColor(ContextCompat.getColor(AssemblyResultActivity.this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);

        String assembly = getIntent().getStringExtra("assembly");
        TextView textViewTitle = findViewById(R.id.text_view_title);
        textViewTitle.setText("Voting Results : Assembly - " + assembly);

        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
        DatabaseReference assemblyVotesNode = rootNode.child("assemblyVotes");

        assemblyVotesNode.child(assembly).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                DataSnapshot data = task.getResult();
                if (data != null && data.exists()) {


                } else {

                    Toast.makeText(getApplicationContext(), "No Votes Exists!", Toast.LENGTH_LONG).show();
                }
            } else {

                Log.e(ApplicationSpecification.name, "firebase : Error getting data", task.getException());
            }
        });

//        ResultModal result1 = new ResultModal(ContextCompat.getDrawable(this, R.drawable.udf), "UDF", 45);
//        ResultModal result2 = new ResultModal(ContextCompat.getDrawable(this, R.drawable.ldf), "LDF", 40);
//        ResultModal result3 = new ResultModal(ContextCompat.getDrawable(this, R.drawable.nda), "NDA", 5);
//
//        results.add(result1);
//        results.add(result2);
//        results.add(result3);

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
