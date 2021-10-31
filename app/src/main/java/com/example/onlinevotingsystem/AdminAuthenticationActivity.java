 package com.example.onlinevotingsystem;

 import android.content.Context;
 import android.content.Intent;
 import android.graphics.drawable.ColorDrawable;
 import android.os.Bundle;
 import android.widget.EditText;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;

 import com.google.firebase.auth.FirebaseAuth;

 public class AdminAuthenticationActivity extends AppCompatActivity {

    Context activityContext = this;
    AppCompatActivity currentActivity = this;

    EditText editTextEmail, editTextPassword;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_authentication);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.design_default_color_primary)));
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

        findViewById(R.id.text_view_sign_in).setOnClickListener(v -> startActivity(new Intent(activityContext, AdminLoginActivity.class)));
    }
}
