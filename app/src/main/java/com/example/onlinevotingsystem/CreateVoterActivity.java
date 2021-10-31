package com.example.onlinevotingsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.example.onlinevotingsystem.CreateAssemblyCandidateActivity.calculateAge;

public class CreateVoterActivity extends AppCompatActivity {

    //variables
    TextView VoterForm, VoterName, Address, Dob, Age, MobileNumber, VoterId, AadharNumber, Parliament, Assembly;

    EditText EnterName, EnterAddress, EnterAge, EnterMobile, EnterVoterId, EnterAadhar;

    MaterialButton submit;

    String genderType = "";

    String dob;

    //writing data to firebase
    FirebaseDatabase rootNode;
    DatabaseReference votersNode;

    //for gender
    RadioButton Male, Female, Other;
    RadioGroup radioGroup;

    //for dependant spinner
    Spinner spinner_parliament, spinner_assembly;
    ArrayList<String> arrayList_parliament;
    ArrayAdapter<String> arrayAdapter_parliament;

    ArrayList<String> arrayList_malappuram, arrayList_ponnani, arrayList_wayanad;
    ArrayAdapter<String> arrayAdapter_assembly;

    //for dob
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voter);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.design_default_color_primary)));

        VoterForm = findViewById(R.id.VoterForm);
        VoterName = findViewById(R.id.VoterName);
        Address = findViewById(R.id.Address);
        Dob = findViewById(R.id.Dob);
        Age = findViewById(R.id.Age);
        MobileNumber = findViewById(R.id.MobileNumber);
        VoterId = findViewById(R.id.VoterId);
        AadharNumber = findViewById(R.id.AadharNumber);
        Parliament = findViewById(R.id.Parliament);
        Assembly = findViewById(R.id.Assembly);

        EnterName = findViewById(R.id.EnterName);
        EnterAddress = findViewById(R.id.EnterAddress);
        EnterAge = findViewById(R.id.EnterAge);
        EnterMobile = findViewById(R.id.EnterMobile);
        EnterVoterId = findViewById(R.id.EnterVoterId);
        EnterAadhar = findViewById(R.id.EnterAadhar);

        submit = findViewById(R.id.button_submit);

        //for dependant spinner
        spinner_parliament = findViewById(R.id.spinner_parliament);
        spinner_assembly = findViewById(R.id.spinner_parliment);

        arrayList_parliament = new ArrayList<>();
        arrayList_parliament.add("Malappuram");
        arrayList_parliament.add("Ponnani");
        arrayList_parliament.add("Wayanad");

        arrayAdapter_parliament = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_parliament);
        spinner_parliament.setAdapter(arrayAdapter_parliament);

        arrayList_malappuram = new ArrayList<>();
        arrayList_malappuram.add("Malappuram");
        arrayList_malappuram.add("Manjeri");
        arrayList_malappuram.add("Mankada");
        arrayList_malappuram.add("Perinthalmanna");
        arrayList_malappuram.add("Vallikunnu");
        arrayList_malappuram.add("Kondotty");
        arrayList_malappuram.add("Vengara");

        arrayList_ponnani = new ArrayList<>();
        arrayList_ponnani.add("Ponnani");
        arrayList_ponnani.add("Tirur");
        arrayList_ponnani.add("Tanur");
        arrayList_ponnani.add("Tirurangadi");
        arrayList_ponnani.add("Kottakkal");
        arrayList_ponnani.add("Thavanur");

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

        //for gender
        Male = findViewById(R.id.male);
        Female = findViewById(R.id.female);
        Other = findViewById(R.id.other);
        radioGroup = findViewById(R.id.RadioGroup);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (Male.isChecked()) {

                genderType = "Male";
                Toast.makeText(CreateVoterActivity.this, "Male", Toast.LENGTH_SHORT).show();

            } else if (Female.isChecked()) {

                genderType = "Female";
                Toast.makeText(CreateVoterActivity.this, "Female", Toast.LENGTH_SHORT).show();

            } else {

                genderType = "Other";
                Toast.makeText(CreateVoterActivity.this, "Other", Toast.LENGTH_SHORT).show();
            }
        });

        //Save data in firebase on button click
        submit.setOnClickListener(v -> {

            //Get all the values
            String name = EnterName.getText().toString();
            String address = EnterAddress.getText().toString();
            String age = EnterAge.getText().toString();
            String mobileNumber = EnterMobile.getText().toString();
            String voterId = EnterVoterId.getText().toString();
            String aadharNumber = EnterAadhar.getText().toString();

            if (name.isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please Enter Name..", Toast.LENGTH_LONG).show();
                EnterName.setError("Please Enter Name..");
                EnterName.requestFocus();

            } else if (address.isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please Enter Address..", Toast.LENGTH_LONG).show();
                EnterAddress.setError("Please Enter Address..");
                EnterAddress.requestFocus();

            } else if (age.isEmpty() || Integer.parseInt(age) < 18) {

                Toast.makeText(getApplicationContext(), "Invalid Age..", Toast.LENGTH_LONG).show();

            } else if (genderType.isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please Select Gender..", Toast.LENGTH_LONG).show();

            } else if (mobileNumber.length() != 10 || (!mobileNumber.matches("[0-9]+"))) {

                Toast.makeText(getApplicationContext(), "Invalid Mobile Number..", Toast.LENGTH_LONG).show();
                EnterMobile.setError("Invalid Mobile Number..");
                EnterMobile.requestFocus();

            } else if (voterId.isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please Enter VoterID..", Toast.LENGTH_LONG).show();
                EnterVoterId.setError("Please Enter VoterID..");
                EnterVoterId.requestFocus();

            } else if (aadharNumber.length() != 12 || (!aadharNumber.matches("[0-9]+"))) {

                Toast.makeText(getApplicationContext(), "Invalid Aadhar Number..", Toast.LENGTH_LONG).show();
                EnterAadhar.setError("Invalid Aadhar Number..");
                EnterAadhar.requestFocus();

            } else {

                try {
                    rootNode = FirebaseDatabase.getInstance();
                    votersNode = rootNode.getReference("voters");

                    String parlimentName = spinner_parliament.getSelectedItem().toString();
                    String assemblyName = spinner_assembly.getSelectedItem().toString();
                    dob = dateButton.getText().toString();

                    VoterInfoModal info = new VoterInfoModal(name, address, age, mobileNumber, voterId, aadharNumber, parlimentName, assemblyName, dob, genderType, false, false);

                    Log.d(ApplicationSpecification.name, "Date : " + dateButton.getText().toString());

                    //TODO : Avoid duplicate voter Ids
                    votersNode.child(voterId).setValue(info);
                    Toast.makeText(CreateVoterActivity.this, "Voter added successfully", Toast.LENGTH_SHORT).show();
                    Log.d(ApplicationSpecification.name, "info : " + info.toString());

                } catch (Exception e) {

                    Toast.makeText(CreateVoterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.d(ApplicationSpecification.name, "Exception : " + e.toString());
                }
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
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {

            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            EnterAge.setText(String.valueOf(calculateAge(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())));
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {

        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {

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
}
