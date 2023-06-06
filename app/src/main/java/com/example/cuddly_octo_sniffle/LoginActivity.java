package com.example.cuddly_octo_sniffle;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//import com.example.cuddly_octo_sniffle.data.ReadAndWriteSnippets;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    //GoogleSignInClient mGoogleSignInClient;
    EditText edLoginEmail;
    EditText edLoginPassword;
    Button btnLoginSubmit;
    TextView tvLoginToSignup;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findTheViews();


        btnLoginSubmit.setOnClickListener(v -> {
            String email = edLoginEmail.getText().toString().trim();
            String password = edLoginPassword.getText().toString().trim();


            // Check if email or password is empty
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Email or password cannot be empty", Toast.LENGTH_SHORT).show();
                return; // Exit the onClick listener
            }


            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this,
                            task -> {
                                if (task.isSuccessful()) {
                                    // Login successful, do something here
                                    Toast.makeText(LoginActivity.this,
                                            "Login succeeded",
                                            Toast.LENGTH_SHORT).show();


                                    startActivity(new Intent(LoginActivity.this,
                                            MainActivity.class));
                                } else {
                                    // Login failed, show error message
                                    Toast.makeText(LoginActivity.this,
                                            "Login failed: " + Objects.requireNonNull(task.getException()).
                                                    getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
        });

        tvLoginToSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

    }

    private void findTheViews() {
        edLoginEmail = findViewById(R.id.ed_login_email);
        edLoginPassword = findViewById(R.id.ed_login_password);
        btnLoginSubmit = findViewById(R.id.btn_login_submit);
        tvLoginToSignup = findViewById(R.id.tv_login_to_signup);

    }

}