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

    TextView tv_signup_title;
    EditText ed_signup_username, ed_signup_email, ed_signup_password, ed_signup_confirm_password;
    Button btn_signup_submit;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findTheViews();

        firebaseAuth = FirebaseAuth.getInstance();

        btn_signup_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_signup_username.getText().toString().trim();
                String email = ed_signup_email.getText().toString().trim();
                String password = ed_signup_password.getText().toString().trim();
                String confirm_password = ed_signup_confirm_password.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    ed_signup_username.setError("Please enter a username");
                    ed_signup_username.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    ed_signup_email.setError("Please enter an email address");
                    ed_signup_email.requestFocus();
                    return;
                }


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ed_signup_email.setError("Please enter a valid email address");
                    ed_signup_email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    ed_signup_password.setError("Please enter a password");
                    ed_signup_password.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    ed_signup_password.setError("Password must be at least 6 characters long");
                    ed_signup_password.requestFocus();
                    return;
                }

                if (!password.equals(confirm_password)) {
                    ed_signup_confirm_password.setError("Passwords do not match");
                    ed_signup_confirm_password.requestFocus();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this,
                                new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign up success,
                                    // update UI with the signed-in user's information
                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
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
                            }
                        });

            }
        });
    }

    private void findTheViews() {
        tv_signup_title = findViewById(R.id.tv_signup_title);
        ed_signup_username = findViewById(R.id.ed_signup_username);
        ed_signup_email = findViewById(R.id.ed_signup_email);
        ed_signup_password = findViewById(R.id.ed_signup_password);
        ed_signup_confirm_password = findViewById(R.id.ed_signup_confirm_password);
        btn_signup_submit = findViewById(R.id.btn_signup_submit);
    }
}