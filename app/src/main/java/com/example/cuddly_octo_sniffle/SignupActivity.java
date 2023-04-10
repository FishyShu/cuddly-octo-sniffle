package com.example.cuddly_octo_sniffle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    TextView tvSignupTitle;
    EditText edSignupUsername;
    EditText edSignupEmail;
    EditText edSignupPassword;
    EditText edSignupConfirmPassword;
    Button btnSignupSubmit;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findTheViews();

        firebaseAuth = FirebaseAuth.getInstance();

        btnSignupSubmit.setOnClickListener(this::onClick);
    }

    private void findTheViews() {
        tvSignupTitle = findViewById(R.id.tv_signup_title);
        edSignupUsername = findViewById(R.id.ed_signup_username);
        edSignupEmail = findViewById(R.id.ed_signup_email);
        edSignupPassword = findViewById(R.id.ed_signup_password);
        edSignupConfirmPassword = findViewById(R.id.ed_signup_confirm_password);
        btnSignupSubmit = findViewById(R.id.btn_signup_submit);
    }

    private void onClick(View v) {
        String username = edSignupUsername.getText().toString().trim();
        String email = edSignupEmail.getText().toString().trim();
        String password = edSignupPassword.getText().toString().trim();
        String confirmPassword = edSignupConfirmPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(username)) {
            if (TextUtils.isEmpty(email)) {
                edSignupEmail.setError("Please enter an email address");
                edSignupEmail.requestFocus();
                return;
            }


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edSignupEmail.setError("Please enter a valid email address");
                edSignupEmail.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                edSignupPassword.setError("Please enter a password");
                edSignupPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                edSignupPassword.setError("Password must be at least 6 characters long");
                edSignupPassword.requestFocus();
                return;
            }

            if (!password.equals(confirmPassword)) {
                edSignupConfirmPassword.setError("Passwords do not match");
                edSignupConfirmPassword.requestFocus();
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignupActivity.this,
                            task -> {
                                if (task.isSuccessful()) {
                                    // Sign up success,
                                    // update UI with the signed-in user's information
                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    UserProfileChangeRequest profileChangeRequest
                                            = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username)
                                            .build();

                                    assert user != null;
                                    user.updateProfile(profileChangeRequest);

                                    // do something with the signed-in user's information
                                    Intent i = new Intent(SignupActivity.this,
                                            LoginActivity.class);

                                    startActivity(i);
                                } else {
                                    Toast.makeText(SignupActivity.this,
                                            "Authentication failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
        } else {
            edSignupUsername.setError("Please enter a username");
            edSignupUsername.requestFocus();
        }

    }
}