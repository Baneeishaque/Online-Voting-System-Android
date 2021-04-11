package com.example.onlinevotingsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateAssemblyCandidateActivity extends AppCompatActivity {

    //variables
    //TODO : Check for under age / over age
    //TODO : Check for mobile number unique
    //TODO : Check for voter id unique
    //TODO : Check for aadhar number unique
    TextView CandidateForm, CandidateName, Address, Dob, Age, MobileNumber, VoterId, AadharNumber, PartyName, Parliament, Assembly;

    EditText EnterName, EnterAddress, EnterAge, EnterMobile, EnterVoterId, EnterAadhar;

    MaterialButton submit;

    String genderType;

    //TODO : Calculate age, avoid age input
    String dob;

    //writing data to firebase
    FirebaseDatabase rootNode;
    DatabaseReference assemblyCandidatesNode;

    //for gender
    RadioButton Male, Female, Other;
    RadioGroup radioGroup;

    //for dependant spinner
    Spinner spinner_parliament, spinner_assembly;

    ArrayList<String> arrayList_parliament;
    ArrayAdapter<String> arrayAdapter_parliament;

    ArrayList<String> arrayList_malappuram, arrayList_ponnani, arrayList_wayanad;
    ArrayAdapter<String> arrayAdapter_assembly;

    //for party name spinner view
    private ArrayList<PartyModal> mPartyList;

    //for dob
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assembly_candidate);

        getWindow().setStatusBarColor(ContextCompat.getColor(CreateAssemblyCandidateActivity.this, R.color.colorAccent));
        if (getSupportActionBar() != null) 
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

        CandidateForm = findViewById(R.id.CandidateForm);
        CandidateName = findViewById(R.id.CandidateName);
        Address = findViewById(R.id.Address);
        Dob = findViewById(R.id.Dob);
        Age = findViewById(R.id.Age);
        MobileNumber = findViewById(R.id.MobileNumber);
        VoterId = findViewById(R.id.VoterId);
        AadharNumber = findViewById(R.id.AadharNumber);
        PartyName = findViewById(R.id.PartyName);
        Parliament = findViewById(R.id.Parliament);
        Assembly = findViewById(R.id.Assembly);

        EnterName = findViewById(R.id.EnterName);
        EnterAddress = findViewById(R.id.EnterAddress);
        EnterAge = findViewById(R.id.EnterAge);
        EnterMobile = findViewById(R.id.EnterMobile);
        EnterVoterId = findViewById(R.id.EnterVoterId);
        EnterAadhar = findViewById(R.id.EnterAadhar);

        submit = findViewById(R.id.submit);

        //for dependant spinner
        spinner_parliament = (Spinner) findViewById(R.id.spinner_parliament);
        spinner_assembly = (Spinner) findViewById(R.id.spinner_assembly);

        //TODO : populate from string array
        arrayList_parliament = new ArrayList<>();
        arrayList_parliament.add("Malappuram");
        arrayList_parliament.add("Ponnani");
        arrayList_parliament.add("Wayanad");

        arrayAdapter_parliament = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_parliament);
        spinner_parliament.setAdapter(arrayAdapter_parliament);

        //TODO : populate from string array
        arrayList_malappuram = new ArrayList<>();
        arrayList_malappuram.add("Malappuram");
        arrayList_malappuram.add("Manjeri");
        arrayList_malappuram.add("Mankada");
        arrayList_malappuram.add("Perinthalmanna");
        arrayList_malappuram.add("Vallikunnu");
        arrayList_malappuram.add("Kondotty");
        arrayList_malappuram.add("Vengara");

        //TODO : populate from string array
        arrayList_ponnani = new ArrayList<>();
        arrayList_ponnani.add("Ponnani");
        arrayList_ponnani.add("Tirur");
        arrayList_ponnani.add("Tanur");
        arrayList_ponnani.add("Tirurangadi");
        arrayList_ponnani.add("Kottakkal");
        arrayList_ponnani.add("Thavanur");

        //TODO : populate from string array
        arrayList_wayanad = new ArrayList<>();
        arrayList_wayanad.add("Eranad");
        arrayList_wayanad.add("Wandoor");
        arrayList_wayanad.add("Nilambur");

        spinner_parliament.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    arrayAdapter_assembly = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_malappuram);
                }
                if (position == 1) {

                    arrayAdapter_assembly = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_ponnani);
                }
                if (position == 2) {

                    arrayAdapter_assembly = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_wayanad);
                }
                spinner_assembly.setAdapter(arrayAdapter_assembly);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //for dob
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());

        //for party name spinner view
        initList();
        Spinner spinnerParties = findViewById(R.id.spinner_parties);
        PartyArrayAdapter mAdapter = new PartyArrayAdapter(this, mPartyList);
        spinnerParties.setAdapter(mAdapter);
        spinnerParties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                PartyModal clickedItem = (PartyModal) parent.getItemAtPosition(position);
                String clickedPartyName = clickedItem.getPartyName();
                Toast.makeText(CreateAssemblyCandidateActivity.this, clickedPartyName + "selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //for gender
        genderType = "Male";
        Male = (RadioButton) findViewById(R.id.male);
        Female = (RadioButton) findViewById(R.id.female);
        Other = (RadioButton) findViewById(R.id.other);
        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (Male.isChecked()) {

                genderType = "Male";
                Toast.makeText(CreateAssemblyCandidateActivity.this, "Male", Toast.LENGTH_SHORT).show();

            } else if (Female.isChecked()) {

                genderType = "Female";
                Toast.makeText(CreateAssemblyCandidateActivity.this, "Female", Toast.LENGTH_SHORT).show();

            } else {

                genderType = "Other";
                Toast.makeText(CreateAssemblyCandidateActivity.this, "Other", Toast.LENGTH_SHORT).show();
            }
        });

        //Save data in firebase on button click
        submit.setOnClickListener(v -> {

            try {
                rootNode = FirebaseDatabase.getInstance();
                assemblyCandidatesNode = rootNode.getReference("assemblyCandidates");

                //Get all the values
                String name = EnterName.getText().toString();
                String address = EnterAddress.getText().toString();
                String age = EnterAge.getText().toString();
                String mobileNumber = EnterMobile.getText().toString();
                String voterId = EnterVoterId.getText().toString();
                String aadharNumber = EnterAadhar.getText().toString();
                PartyModal party = (PartyModal) spinnerParties.getSelectedItem();
                String partyName = party.getPartyName();
                String parlimentName = spinner_parliament.getSelectedItem().toString();
                String assemblyName = spinner_assembly.getSelectedItem().toString();
                dob = dateButton.getText().toString();

                CandidateInfoModal info = new CandidateInfoModal(name, address, age, mobileNumber, voterId, aadharNumber, parlimentName, partyName, assemblyName, dob, genderType);

                Log.d(ApplicationSpecification.name, "DATE : " + dateButton.getText().toString());

                //TODO : Avoid duplicate voter Ids - check for voterId existence
                //TODO : Check returns from firebase db
                assemblyCandidatesNode.child(assemblyName).child(voterId).setValue(info);
                //TODO : Reset from after submission
                Toast.makeText(CreateAssemblyCandidateActivity.this, "Candidate added successfully", Toast.LENGTH_SHORT).show();
                Log.d(ApplicationSpecification.name, "info : " + info.toString());

            } catch (Exception e) {

                Toast.makeText(CreateAssemblyCandidateActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d(ApplicationSpecification.name, "Exception : " + e.toString());
            }
        });
    }

    //for dob
    private String getTodayDate() {

        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //TODO : Use SimpleDateFormat Patterns
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        //TODO : Use SimpleDateFormat Patterns
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {

        //TODO : Use SimpleDateFormat Patterns
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {

        //TODO : Use SimpleDateFormat Patterns
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view) {

        datePickerDialog.show();
    }

    //for party name spinner view
    private void initList() {

        //TODO : populate from string array
        mPartyList = new ArrayList<>();
        mPartyList.add(new PartyModal("LDF", R.drawable.ldf));
        mPartyList.add(new PartyModal("UDF", R.drawable.udf));
        mPartyList.add(new PartyModal("NDA", R.drawable.nda));
    }
}
