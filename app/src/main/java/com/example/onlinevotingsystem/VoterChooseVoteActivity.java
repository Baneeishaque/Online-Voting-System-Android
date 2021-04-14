package com.example.onlinevotingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VoterChooseVoteActivity extends AppCompatActivity {

    Context activityContext = this;

//    public static String type, typeValue, voterId;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_choose_vote);

        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.button_vote_assembly).setOnClickListener(v -> {

            AdminHomeActivity.checkVotingTime(VoterChooseVoteActivity.this, progressBar, getApplicationContext(), () -> {

                DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
                DatabaseReference votersNode = rootNode.child("voters");

                votersNode.child(getIntent().getStringExtra("voterId")).get().addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        DataSnapshot data = task.getResult();

                        VoterInfoModal voter = data.getValue(VoterInfoModal.class);

                        Log.d(ApplicationSpecification.name, "Voter : " + voter);

                        if (voter.isAssemblyVoteDone) {

                            Toast.makeText(getApplicationContext(), "Already over!", Toast.LENGTH_LONG).show();

                        } else {

                            Intent intent = new Intent(activityContext, VoterVotingActivity.class);

                            intent.putExtra("type", "assembly");
                            intent.putExtra("typeValue", getIntent().getStringExtra("assembly"));
                            intent.putExtra("voterId", getIntent().getStringExtra("voterId"));

                            // type = "assembly";
                            // typeValue = voter.getAssemblyName();
                            // voterId = voter.getVoterId();

                            // VoterVotingActivity.type = "assembly";
                            // VoterVotingActivity.typeValue = voter.getAssemblyName();
                            // VoterVotingActivity.voterId = voter.getVoterId();

                            // Log.d(ApplicationSpecification.name, "type : " + type);
                            // Log.d(ApplicationSpecification.name, "typeValue : " + typeValue);

                            startActivity(intent);
                        }

                    } else {

                        Log.e(ApplicationSpecification.name, "firebase : Error getting data", task.getException());
                    }
                });
            });
        });


        findViewById(R.id.button_vote_parliment).setOnClickListener(v -> {

            AdminHomeActivity.checkVotingTime(VoterChooseVoteActivity.this, progressBar, getApplicationContext(), () -> {

                DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
                DatabaseReference votersNode = rootNode.child("voters");

                votersNode.child(getIntent().getStringExtra("voterId")).get().addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        DataSnapshot data = task.getResult();

                        VoterInfoModal voter = data.getValue(VoterInfoModal.class);

                        Log.d(ApplicationSpecification.name, "Voter : " + voter);

                        if (voter.isParlimentVoteDone) {

                            Toast.makeText(getApplicationContext(), "Already over!", Toast.LENGTH_LONG).show();

                        } else {

                            Intent intent = new Intent(activityContext, VoterVotingActivity.class);

                            intent.putExtra("type", "parliment");
                            intent.putExtra("typeValue", getIntent().getStringExtra("parliment"));
                            intent.putExtra("voterId", getIntent().getStringExtra("voterId"));

                            startActivity(intent);
                        }

                    } else {

                        Log.e(ApplicationSpecification.name, "firebase : Error getting data", task.getException());
                    }
                });
            });
        });
    }
}
