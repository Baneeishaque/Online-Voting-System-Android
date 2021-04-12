package com.example.onlinevotingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VoterVotingActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public RecyclerView recyclerView;

    // public static String type, typeValue, voterId;

    Context activityContext = this;

    ArrayList<CandidateModal> candidates = new ArrayList<>();
    private final CandidatesRecyclerViewAdaptor candidatesRecyclerViewAdaptor = new CandidatesRecyclerViewAdaptor(candidates);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_voting);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);

        // Log.d(ApplicationSpecification.name, "type : " + type);
        // Log.d(ApplicationSpecification.name, "typeValue : " + typeValue);

//        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference typeCandidatesNode = rootNode.child("assembly" + "Candidates");
//
//        typeCandidatesNode.child("Malappuram").get().addOnCompleteListener(task -> {
//
//            if (task.isSuccessful()) {
//
//                DataSnapshot data = task.getResult();
//                if (data != null && data.exists()) {
//
//                    Log.d(ApplicationSpecification.name, "Data : " + data.toString());
//
////                    for (DataSnapshot snapshot : data.getChildren()) {
////
////                        CandidateInfoModal candidate = snapshot.getValue(CandidateInfoModal.class);
////                        candidates.add(new CandidateModal(getPartySymbol(candidate.partyName), candidate.name));
////                    }
//                } else {
//
//                    Toast.makeText(getApplicationContext(), "No Candidates...", Toast.LENGTH_LONG).show();
//                }
//            } else {
//
//                Log.e(ApplicationSpecification.name, "firebase : Error getting data", task.getException());
//            }
//
//        });

//        CandidateModal candidate1 = new CandidateModal(ContextCompat.getDrawable(this, R.drawable.udf), "UDF");
//        CandidateModal candidate2 = new CandidateModal(ContextCompat.getDrawable(this, R.drawable.ldf), "LDF");
//
//        candidates.add(candidate1);
//        candidates.add(candidate2);

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

    public Drawable getPartySymbol(String partyName) {

        switch (partyName) {

            case "LDF":
                return ContextCompat.getDrawable(activityContext, R.drawable.ldf);
            case "NDA":
                return ContextCompat.getDrawable(activityContext, R.drawable.nda);
            case "UDF":
            default:
                return ContextCompat.getDrawable(activityContext, R.drawable.udf);
        }
    }
}
