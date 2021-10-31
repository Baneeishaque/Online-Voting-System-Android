package com.example.onlinevotingsystem;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static com.example.onlinevotingsystem.AssemblyResultActivity.calculateWinners;
import static com.example.onlinevotingsystem.AssemblyResultActivity.getFirstItemValueFromJsonObject;
import static com.example.onlinevotingsystem.AssemblyResultActivity.presentWinners;
import static com.example.onlinevotingsystem.VoterVotingActivity.getPartySymbol;

public class ParlimentResultActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public RecyclerView recyclerView;

    ArrayList<ResultModal> results = new ArrayList<>();
    private final ResultsRecyclerViewAdaptor resultsRecyclerViewAdaptor = new ResultsRecyclerViewAdaptor(results);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.design_default_color_primary)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);

        String parliment = getIntent().getStringExtra("parliment");
        TextView textViewTitle = findViewById(R.id.text_view_title);
        textViewTitle.setText("Results : Parliament - " + parliment);

//        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference parlimentVotesNode = rootNode.child("parlimentVotes");
//
//        parlimentVotesNode.child(parliment).get().addOnCompleteListener(task -> {
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
        new GetParlimentResultsNetworkTask(parliment, this, progressBar, recyclerView, response -> {

//                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
            Log.d(ApplicationSpecification.name, "Response : " + response);

            if (response.equals("null")) {

                Toast.makeText(getApplicationContext(), "No Votes!", Toast.LENGTH_LONG).show();

            } else {

                try {

                    JSONObject candidatesJsonObject = new JSONObject(response);
                    Log.d(ApplicationSpecification.name, "Parliment " + parliment + " Votes JSON Object : " + candidatesJsonObject);

                    Iterator<?> keys = candidatesJsonObject.keys();
                    while (keys.hasNext()) {
                        String candidate = (String) keys.next();
                        if (candidatesJsonObject.get(candidate) instanceof JSONObject) {

                            JSONObject candidateVotes = new JSONObject(candidatesJsonObject.get(candidate).toString());
                            Log.d(ApplicationSpecification.name, "Parliment Candidate - " + candidate + " Votes : " + candidateVotes);
                            results.add(new ResultModal(getPartySymbol(getFirstItemValueFromJsonObject(candidateVotes), ParlimentResultActivity.this), candidate, candidateVotes.length()));
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
}
