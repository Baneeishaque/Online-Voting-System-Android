 package com.example.onlinevotingsystem;

import android.annotation.SuppressLint;
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

import java.util.Objects;

public class AdminAuthenticationActivity extends AppCompatActivity {

    Context activityContext = this;
    AppCompatActivity currentActivity = this;

    EditText editTextEmail, editTextPassword;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_authentication);

        getWindow().setStatusBarColor(ContextCompat.getColor(activityContext, R.color.colorAccent));
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);

        findViewById(R.id.button_sign_up).setOnClickListener(v -> {

            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (email.isEmpty() && password.isEmpty()) {

                Toast.makeText(getApplicationContext(), "Fields Are Empty!", Toast.LENGTH_SHORT).show();

            } else if (email.isEmpty()) {

                editTextEmail.setError("Please enter email id...");
                editTextEmail.requestFocus();

            } else if (password.isEmpty()) {

                editTextPassword.setError("Please enter your password...");
                editTextPassword.requestFocus();

            } else {

                mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(currentActivity, task -> {

                    if (!task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "SignUp Unsuccessful, Please Try Again...", Toast.LENGTH_SHORT).show();

                    } else {

                        startActivity(new Intent(activityContext, AdminHomeActivity.class));
                    }
                });

            }
        });

        findViewById(R.id.text_view_sign_in).setOnClickListener(v -> {

            startActivity(new Intent(activityContext, AdminLoginActivity.class));
        });
    }
}
