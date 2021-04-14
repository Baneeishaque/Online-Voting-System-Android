package com.example.onlinevotingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeConfigurationActivity extends AppCompatActivity {

    public ProgressBar progressBar;

    Button buttonStartTime, buttonEndTime;
    boolean isStartTimeSelected = false, isEndTimeSelected = false;

    public static SimpleDateFormat simpleDateTime = new SimpleDateFormat("MMM. dd. yyyy hh:mm a", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_configuration);

        progressBar = findViewById(R.id.progressBar);

        buttonStartTime = findViewById(R.id.button_start_time);
        buttonEndTime = findViewById(R.id.button_end_time);

        SendOtpNetworkTask.showProgress(true, this, progressBar);
        new GetTimeConfigurationNetworkTask(this, progressBar, response -> {

//                    Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_LONG).show();
            Log.d(ApplicationSpecification.name, "Response : " + response);

            if (response.equals("null")) {

                Toast.makeText(getApplicationContext(), "No Time Configuration!", Toast.LENGTH_LONG).show();

            } else {

                try {

                    JSONObject timeConfigurationJsonObject = new JSONObject(response);
                    Log.d(ApplicationSpecification.name, "Time Configuration JSON Object : " + timeConfigurationJsonObject.toString());

                    buttonStartTime.setText(timeConfigurationJsonObject.getString("startTime"));
                    buttonEndTime.setText(timeConfigurationJsonObject.getString("endTime"));

                } catch (JSONException e) {
                    Log.d(ApplicationSpecification.name, "Exception : " + e.getLocalizedMessage());
                }
            }
        }).execute();

        SwitchDateTimeDialogFragment dateTimeDialogFragmentStartTime = SwitchDateTimeDialogFragment.newInstance("Pick Start Time", "OK", "Cancel");

        SwitchDateTimeDialogFragment dateTimeDialogFragmentEndTime = SwitchDateTimeDialogFragment.newInstance("Pick End Time", "OK", "Cancel");

//        dateTimeDialogFragmentStartTime.setMinimumDateTime(Calendar.getInstance().getTime());
        dateTimeDialogFragmentStartTime.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {

            @Override
            public void onPositiveButtonClick(Date date) {

                buttonStartTime.setText(simpleDateTime.format(date));
//                dateTimeDialogFragmentEndTime.setMinimumDateTime(DateUtils.addMinutes(date, 15));
                isStartTimeSelected = true;
                buttonEndTime.setText("END TIME");
                isEndTimeSelected = false;
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }
        });

        dateTimeDialogFragmentEndTime.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {

            @Override
            public void onPositiveButtonClick(Date date) {

                buttonEndTime.setText(simpleDateTime.format(date));
                isEndTimeSelected = true;
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }
        });


        buttonStartTime.setOnClickListener(v -> {

            dateTimeDialogFragmentStartTime.show(getSupportFragmentManager(), "DateTimePickerStartTime");
        });

        buttonEndTime.setOnClickListener(v -> {

            if (isStartTimeSelected) {

                dateTimeDialogFragmentEndTime.show(getSupportFragmentManager(), "DateTimePickerEndTime");

            } else {

                Toast.makeText(TimeConfigurationActivity.this, "Please select start time...", Toast.LENGTH_LONG).show();
            }

        });

        findViewById(R.id.button_submit).setOnClickListener(v -> {

            if (isStartTimeSelected) {

                if (isEndTimeSelected) {

                    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                    DatabaseReference configurationNode = rootNode.getReference("configuration");

                    configurationNode.setValue(new TimeConfigurationModal(buttonStartTime.getText().toString(), buttonEndTime.getText().toString()));
                    Toast.makeText(this, "Time configured successfully", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(TimeConfigurationActivity.this, "Please select start time...", Toast.LENGTH_LONG).show();
                }

            } else {

                Toast.makeText(TimeConfigurationActivity.this, "Please select start time...", Toast.LENGTH_LONG).show();
            }
        });
    }
}