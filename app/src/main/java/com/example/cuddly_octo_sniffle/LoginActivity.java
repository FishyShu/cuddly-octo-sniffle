package com.example.cuddly_octo_sniffle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    EditText ed_login_email, ed_login_password;
    Button btn_login_submit;
    TextView tv_login_to_signup;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findTheViews();


        btn_login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ed_login_email.getText().toString().trim();
                String password = ed_login_password.getText().toString().trim();


                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this,
                                new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
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
                                            "Login failed: " + task.getException().
                                                    getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        tv_login_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void findTheViews() {
        ed_login_email = findViewById(R.id.ed_login_email);
        ed_login_password = findViewById(R.id.ed_login_password);
        btn_login_submit = findViewById(R.id.btn_login_submit);
        tv_login_to_signup = findViewById(R.id.tv_login_to_signup);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public static final int RC_SIGN_IN = 4921482;
}