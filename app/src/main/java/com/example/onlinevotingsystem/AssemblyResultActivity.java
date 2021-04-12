package com.example.onlinevotingsystem;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static com.example.onlinevotingsystem.VoterVotingActivity.getPartySymbol;

public class AssemblyResultActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;

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
        textViewTitle.setText("Results : Assembly - " + assembly);

//        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference assemblyVotesNode = rootNode.child("assemblyVotes");
//
//        assemblyVotesNode.child(assembly).get().addOnCompleteListener(task -> {
//
//            if (task.isSuccessful()) {
//
//                DataSnapshot data = task.getResult();
//                if (data != null && data.exists()) {
//
//
//                } else {
//
//                    Toast.makeText(getApplicationContext(), "No Votes Exists!", Toast.LENGTH_LONG).show();
//                }
//            } else {
//
//                Log.e(ApplicationSpecification.name, "firebase : Error getting data", task.getException());
//            }
//        });

        SendOtpNetworkTask.showProgress(true, this, progressBar, recyclerView);
        new GetAssemblyResultsNetworkTask(assembly, this, progressBar, recyclerView, response -> {

//                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
            Log.d(ApplicationSpecification.name, "Response : " + response);

            if (response.equals("null")) {

                Toast.makeText(getApplicationContext(), "No Votes!", Toast.LENGTH_LONG).show();

            } else {

                try {

                    JSONObject candidatesJsonObject = new JSONObject(response);
                    Log.d(ApplicationSpecification.name, "Assembly " + assembly + " Votes JSON Object : " + candidatesJsonObject.toString());

                    Iterator<?> keys = candidatesJsonObject.keys();
                    while (keys.hasNext()) {
                        String candidate = (String) keys.next();
                        if (candidatesJsonObject.get(candidate) instanceof JSONObject) {

                            JSONObject candidateVotes = new JSONObject(candidatesJsonObject.get(candidate).toString());
                            Log.d(ApplicationSpecification.name, "Assembly Candidate - " + candidate + " Votes : " + candidateVotes.toString());
                            results.add(new ResultModal(getPartySymbol(getFirstItemValueFromJsonObject(candidateVotes), AssemblyResultActivity.this), candidate, candidateVotes.length()));
                        }
                    }

                    ArrayList<ResultModal> winners = calculateWinners(results);

                    findViewById(R.id.constraintLayout).setVisibility(View.VISIBLE);
                    ImageView imageViewWinnerPartySymbol = findViewById(R.id.item_image_candidate_symbol);
                    TextView textViewWinnerName = findViewById(R.id.item_text_candidate_name);

                    presentWinners(winners, imageViewWinnerPartySymbol, textViewWinnerName);

                } catch (JSONException e) {
                    Log.d(ApplicationSpecification.name, "Exception : " + e.getLocalizedMessage());
                }
            }
        }).execute();

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

    public static void presentWinners(ArrayList<ResultModal> winners, ImageView imageViewWinnerPartySymbol, TextView textViewWinnerName) {

        if (winners.size() > 1) {

            imageViewWinnerPartySymbol.setVisibility(View.INVISIBLE);
            String winnerList = "";
            int i = 0;
            for (ResultModal winner : winners) {

                if (i == 0) {

                    winnerList = winner.name;

                } else {
                    winnerList = winnerList + ", " + winner.name;
                }
                i++;
            }
            textViewWinnerName.setText(winnerList + " Wins");

        } else {

            imageViewWinnerPartySymbol.setImageDrawable(winners.get(0).partySymbol);
            textViewWinnerName.setText(winners.get(0).name + " Wins");
        }
    }

    public static String getFirstItemValueFromJsonObject(JSONObject jsonObject) {

        String result = "";
        //length must be greater than 0
        Iterator<?> keys = jsonObject.keys();
        while (keys.hasNext()) {

            String key = (String) keys.next();
            try {

                result = jsonObject.get(key).toString();

            } catch (JSONException e) {

                Log.d(ApplicationSpecification.name, "Exception : " + e.getLocalizedMessage());
            }
        }
        return result;
    }

    public static ArrayList<ResultModal> calculateWinners(ArrayList<ResultModal> votingResults) {

        //length must be greater than 0
        ResultModal winnerResult = votingResults.get(0);
        ArrayList<ResultModal> winners = new ArrayList<>();
        winners.add(winnerResult);
        for (int i = 1; i < votingResults.size(); i++) {

            ResultModal nextResult = votingResults.get(i);
            if (nextResult.noOfVotes == winnerResult.noOfVotes) {

                winners.add(nextResult);

            } else if (nextResult.noOfVotes > winnerResult.noOfVotes) {

                winnerResult = nextResult;
                winners = new ArrayList<>();
                winners.add(nextResult);
            }
        }
        return winners;
    }
}
