package com.example.onlinevotingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class VoterVotingActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public RecyclerView recyclerView;

    // public static String type, typeValue, voterId;

    Context activityContext = this;

    ArrayList<CandidateModal> candidates = new ArrayList<>();
    private final CandidatesRecyclerViewAdaptor candidatesRecyclerViewAdaptor = new CandidatesRecyclerViewAdaptor(candidates);
    private FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_voting);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);

        // Log.d(ApplicationSpecification.name, "type : " + type);
        // Log.d(ApplicationSpecification.name, "typeValue : " + typeValue);

        String voteType = getIntent().getStringExtra("type");
        String voteTypeValue = getIntent().getStringExtra("typeValue");
        String voterId = getIntent().getStringExtra("voterId");
        Log.d(ApplicationSpecification.name, "Type : " + voteType + ", Type Value : " + voteTypeValue + ", Voter ID : " + voterId);

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

        if (voteType.equals("assembly")) {

            SendOtpNetworkTask.showProgress(true, activityContext, progressBar, recyclerView);
            new GetAssemblyCandidatesNetworkTask(voteTypeValue, activityContext, progressBar, recyclerView, response -> {

//                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
                Log.d(ApplicationSpecification.name, "Response : " + response);

                if (response.equals("null")) {

                    Toast.makeText(VoterVotingActivity.this.getApplicationContext(), "No Candidates!", Toast.LENGTH_LONG).show();

                } else {

                    try {

                        JSONObject assemblyCandidatesJsonObject = new JSONObject(response);
                        Log.d(ApplicationSpecification.name, "Assembly " + voteTypeValue + " Candidates JSON Object : " + assemblyCandidatesJsonObject.toString());

                        Iterator<?> keys = assemblyCandidatesJsonObject.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            if (assemblyCandidatesJsonObject.get(key) instanceof JSONObject) {

                                JSONObject assemblyCandidate = new JSONObject(assemblyCandidatesJsonObject.get(key).toString());
                                Log.d(ApplicationSpecification.name, "Assembly Candidate : " + assemblyCandidate.toString());
                                candidates.add(new CandidateModal(getPartySymbol(assemblyCandidate.getString("partyName"), activityContext), assemblyCandidate.getString("name"), assemblyCandidate.getString("assemblyName"), assemblyCandidate.getString("parliamentName"), assemblyCandidate.getString("partyName")));
                            }
                        }
                    } catch (JSONException e) {
                        Log.d(ApplicationSpecification.name, "Exception : " + e.getLocalizedMessage());
                    }
                }
            }).execute();

        } else if (voteType.equals("parliment")) {

            SendOtpNetworkTask.showProgress(true, activityContext, progressBar, recyclerView);
            new GetParlimentCandidatesNetworkTask(voteTypeValue, activityContext, progressBar, recyclerView, response -> {

//                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
                Log.d(ApplicationSpecification.name, "Response : " + response);

                if (response.equals("null")) {

                    Toast.makeText(VoterVotingActivity.this.getApplicationContext(), "No Candidates!", Toast.LENGTH_LONG).show();

                } else {

                    try {

                        JSONObject parlimentCandidatesJsonObject = new JSONObject(response);
                        Log.d(ApplicationSpecification.name, "Parliment " + voteTypeValue + " Candidates JSON Object : " + parlimentCandidatesJsonObject.toString());

                        Iterator<?> keys = parlimentCandidatesJsonObject.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            if (parlimentCandidatesJsonObject.get(key) instanceof JSONObject) {

                                JSONObject parlimentCandidate = new JSONObject(parlimentCandidatesJsonObject.get(key).toString());
                                Log.d(ApplicationSpecification.name, "Parliment Candidate : " + parlimentCandidate.toString());
                                candidates.add(new CandidateModal(getPartySymbol(parlimentCandidate.getString("partyName"), activityContext), parlimentCandidate.getString("name"), parlimentCandidate.getString("assemblyName"), parlimentCandidate.getString("parliamentName"), parlimentCandidate.getString("partyName")));
                            }
                        }
                    } catch (JSONException e) {
                        Log.d(ApplicationSpecification.name, "Exception : " + e.getLocalizedMessage());
                    }
                }
            }).execute();
        }

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

            rootNode = FirebaseDatabase.getInstance();
            DatabaseReference votersNode = rootNode.getReference("voters");
            if (voteType.equals("assembly")) {

                DatabaseReference assemblyVotesNode = rootNode.getReference("assemblyVotes");

                assemblyVotesNode.child(candidate.assemblyName).child(candidate.name).push().setValue(candidate.partyName);

                votersNode.child(voterId).child("assemblyVoteDone").setValue(true);

                Toast.makeText(getApplicationContext(), "Vote submitted successfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(activityContext, MainActivity.class));
                this.finish();

            } else if (voteType.equals("parliment")) {

                rootNode = FirebaseDatabase.getInstance();
                DatabaseReference parlimentVotesNode = rootNode.getReference("parlimentVotes");

                parlimentVotesNode.child(candidate.parliment).child(candidate.name).push().setValue(candidate.partyName);

                votersNode.child(voterId).child("parlimentVoteDone").setValue(true);

                Toast.makeText(getApplicationContext(), "Vote submitted successfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(activityContext, MainActivity.class));
                this.finish();
            }
        });
    }

    public static Drawable getPartySymbol(String partyName, Context activityContext) {

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
