package com.example.onlinevotingsystem;

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
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AdminLoginActivity extends AppCompatActivity {

    EditText Email;
    EditText Password;
    MaterialButton btnSignIn;
    TextView tvSignUp;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getWindow().setStatusBarColor(ContextCompat.getColor(AdminLoginActivity.this, R.color.colorAccent));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);

        mAuthStateListener = firebaseAuth -> {

            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser != null) {

                Toast.makeText(AdminLoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                startActivity(i);

            } else {

                Toast.makeText(AdminLoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
            }
        };

        btnSignIn.setOnClickListener(v -> {

            String email = Email.getText().toString();
            String pwd = Password.getText().toString();

            if (email.isEmpty() && pwd.isEmpty()) {

                Toast.makeText(AdminLoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();

            } else if (email.isEmpty()) {

                Email.setError("Please enter email id");
                Email.requestFocus();

            } else if (pwd.isEmpty()) {

                Password.setError("Please enter your password");
                Password.requestFocus();

            } else {

                mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(AdminLoginActivity.this, task -> {

                    if (!task.isSuccessful()) {

                        Toast.makeText(AdminLoginActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();

                    } else {

                        Intent intToMain = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                        startActivity(intToMain);
                    }
                });
            }
//            else {
//                Toast.makeText(AdminloginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
//            }
        });

        tvSignUp.setOnClickListener(v -> {

            Intent intSignUp = new Intent(AdminLoginActivity.this, AdminAuthenticationActivity.class);
            startActivity(intSignUp);
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }
}
