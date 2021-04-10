package com.example.onlinevotingsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    EditText Email;
    EditText Password;
    TextView tvSignIn;
    MaterialButton btnSignUp;
    FirebaseAuth mFirebaseAuth;

    @SuppressLint("ShowToast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getWindow().setStatusBarColor(ContextCompat.getColor(AdminActivity.this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        tvSignIn = findViewById(R.id.tvSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {

            String email = Email.getText().toString();
            String pwd = Password.getText().toString();

            if (email.isEmpty() && pwd.isEmpty()) {

                Toast.makeText(AdminActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();

            } else if (email.isEmpty()) {

                Email.setError("Please enter email id");
                Email.requestFocus();

            } else if (pwd.isEmpty()) {

                Password.setError("Please enter your password");
                Password.requestFocus();

            } else {

                mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(AdminActivity.this, task -> {

                    if (!task.isSuccessful()) {

                        Toast.makeText(AdminActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();

                    } else {

                        startActivity(new Intent(AdminActivity.this, HomeActivity.class));
                    }
                });

            }
//            else {
//                Toast.makeText(AdminActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
//
//            }
        });

        tvSignIn.setOnClickListener(v -> {

            Intent i = new Intent(AdminActivity.this, AdminLoginActivity.class);
            startActivity(i);
        });
    }
}



