package com.example.onlinevotingsystem;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity {

    Context activityContext = this;
    AppCompatActivity currentActivity = this;

    EditText Email,Password;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getWindow().setStatusBarColor(ContextCompat.getColor(activityContext, R.color.colorAccent));
        if (getSupportActionBar() != null) getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);

        mAuthStateListener = firebaseAuth -> {

            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null) {

                Toast.makeText(getApplicationContext(), "You are logged in", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activityContext, AdminHomeActivity.class));

            } else {

                Toast.makeText(getApplicationContext(), "Please Login", Toast.LENGTH_SHORT).show();
            }
        };

        findViewById(R.id.btnSignIn).setOnClickListener(v -> {

            String email = Email.getText().toString();
            String pwd = Password.getText().toString();

            if (email.isEmpty() && pwd.isEmpty()) {

                Toast.makeText(getApplicationContext(), "Fields Are Empty!", Toast.LENGTH_SHORT).show();

            } else if (email.isEmpty()) {

                Email.setError("Please enter email id...");
                Email.requestFocus();

            } else if (pwd.isEmpty()) {

                Password.setError("Please enter your password...");
                Password.requestFocus();

            } else {

                mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(currentActivity, task -> {

                    if (!task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();

                    } else {

                        startActivity(new Intent(activityContext, AdminHomeActivity.class));
                    }
                });
            }
        });

        findViewById(R.id.tvSignUp).setOnClickListener(v -> {

            startActivity(new Intent(activityContext, AdminAuthenticationActivity.class));
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
