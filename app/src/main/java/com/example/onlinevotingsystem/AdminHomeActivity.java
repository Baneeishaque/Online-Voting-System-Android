package com.example.onlinevotingsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AdminHomeActivity extends AppCompatActivity {

    // TODO : Change username & password of admin
    // TODO : Forgot password for admin
    // TODO : Manage other admins

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getWindow().setStatusBarColor(ContextCompat.getColor(AdminHomeActivity.this, R.color.colorAccent));
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.button_logout).setOnClickListener(v -> {

            // TODO : Evaluate Returns for firebase actions
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminHomeActivity.this, AdminAuthenticationActivity.class));
        });

        findViewById(R.id.button_create_assembly_candidate).setOnClickListener(v -> {

            startActivity(new Intent(AdminHomeActivity.this, CreateAssemblyCandidateActivity.class));
        });

        findViewById(R.id.button_create_parliment_candidate).setOnClickListener(v -> {

            startActivity(new Intent(AdminHomeActivity.this, CreateParlimentCandidateActivity.class));
        });

        findViewById(R.id.button_create_voter).setOnClickListener(v -> {

            startActivity(new Intent(AdminHomeActivity.this, CreateVoterActivity.class));
        });

        findViewById(R.id.button_view_assembly_results).setOnClickListener(v -> {

            checkResultTime(this, progressBar, getApplicationContext(), () -> startActivity(new Intent(AdminHomeActivity.this, ChooseAssemblyResultsActivity.class)));
        });

        findViewById(R.id.button_view_parliment_results).setOnClickListener(v -> {

            checkResultTime(this, progressBar, getApplicationContext(), () -> startActivity(new Intent(AdminHomeActivity.this, ChooseParlimentResultsActivity.class)));
        });

        findViewById(R.id.button_time_configuration).setOnClickListener(v -> {

            startActivity(new Intent(AdminHomeActivity.this, TimeConfigurationActivity.class));
        });
    }

    public static void checkVotingTime(Context activityContext, ProgressBar progressBar, Context applicationContext, FurtherActions furtherActions) {

        SendOtpNetworkTask.showProgress(true, activityContext, progressBar);
        new GetTimeConfigurationNetworkTask(activityContext, progressBar, response -> {

//                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
            Log.d(ApplicationSpecification.name, "Response : " + response);

            if (response.equals("null")) {

                Toast.makeText(applicationContext, "No Time Configuration!", Toast.LENGTH_LONG).show();

            } else {

                try {

                    JSONObject timeConfigurationJsonObject = new JSONObject(response);
                    Log.d(ApplicationSpecification.name, "Time Configuration JSON Object : " + timeConfigurationJsonObject.toString());

                    Date currentDateTime = Calendar.getInstance().getTime();
                    if ((currentDateTime.compareTo(TimeConfigurationActivity.simpleDateTime.parse(timeConfigurationJsonObject.getString("startTime"))) > 0) && (currentDateTime.compareTo(TimeConfigurationActivity.simpleDateTime.parse(timeConfigurationJsonObject.getString("endTime"))) < 0)) {

                        furtherActions.furtherAction();

                    } else {

                        Toast.makeText(applicationContext, "Incorrect timings!", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException | ParseException e) {

                    Log.d(ApplicationSpecification.name, "Exception : " + e.getLocalizedMessage());
                }
            }
        }).execute();
    }

    public static void checkResultTime(Context activityContext, ProgressBar progressBar, Context applicationContext, FurtherActions furtherActions) {

        SendOtpNetworkTask.showProgress(true, activityContext, progressBar);
        new GetTimeConfigurationNetworkTask(activityContext, progressBar, response -> {

//                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
            Log.d(ApplicationSpecification.name, "Response : " + response);

            if (response.equals("null")) {

                Toast.makeText(applicationContext, "No Time Configuration!", Toast.LENGTH_LONG).show();

            } else {

                try {

                    JSONObject timeConfigurationJsonObject = new JSONObject(response);
                    Log.d(ApplicationSpecification.name, "Time Configuration JSON Object : " + timeConfigurationJsonObject.toString());

                    Date currentDateTime = Calendar.getInstance().getTime();
                    if (currentDateTime.compareTo(TimeConfigurationActivity.simpleDateTime.parse(timeConfigurationJsonObject.getString("endTime"))) > 0) {

                        furtherActions.furtherAction();

                    } else {

                        Toast.makeText(applicationContext, "Voting is not over!", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException | ParseException e) {

                    Log.d(ApplicationSpecification.name, "Exception : " + e.getLocalizedMessage());
                }
            }
        }).execute();
    }

    interface FurtherActions {

        void furtherAction();
    }
}
