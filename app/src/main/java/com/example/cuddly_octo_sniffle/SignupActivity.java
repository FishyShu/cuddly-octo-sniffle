package com.example.cuddly_octo_sniffle;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    TextView tvSignupTitle, tvSignupLogin;
    EditText edSignupUsername;
    EditText edSignupEmail;
    EditText edSignupPassword;
    EditText edSignupConfirmPassword;

    //CheckBox cbSignupIsTeacher;
    Button btnSignupSubmit;
    FirebaseAuth firebaseAuth;

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findTheViews();

        firebaseAuth = FirebaseAuth.getInstance();

        btnSignupSubmit.setOnClickListener(this::onClick);

        tvSignupLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void findTheViews() {
        tvSignupTitle = findViewById(R.id.tv_signup_title);
        edSignupUsername = findViewById(R.id.ed_signup_username);
        edSignupEmail = findViewById(R.id.ed_signup_email);
        edSignupPassword = findViewById(R.id.ed_signup_password);
        edSignupConfirmPassword = findViewById(R.id.ed_signup_confirm_password);
        btnSignupSubmit = findViewById(R.id.btn_signup_submit);
        tvSignupLogin = findViewById(R.id.tv_signup_login);
        //cbSignupIsTeacher = findViewById(R.id.cb_signup_is_teacher);
    }

    private void onClick(View v) {
        String username = edSignupUsername.getText().toString().trim();
        String email = edSignupEmail.getText().toString().trim();
        String password = edSignupPassword.getText().toString().trim();
        String confirmPassword = edSignupConfirmPassword.getText().toString().trim();
        Boolean isTeacher = false; //cbSignupIsTeacher.isChecked();

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
                                    // adds the user's name to the authentication
                                    UserProfileChangeRequest profileChangeRequest
                                            = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username)
                                            .build();

                                    assert user != null;
                                    user.updateProfile(profileChangeRequest);

                                    // do something with the signed-in user's information
                                    // 11/4: sure thing buddy :)

                                    fireStoreStuff(username, email, isTeacher);


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

    private void fireStoreStuff(String username, String email, Boolean isTeacher) {

        // Reference to the document "known-users" in the "users" collection
        DocumentReference docRef = fireStore.collection("users").document("known-users");

        // Replace dot character with underscore character in email
        String emailKey = email.replace(".", "_");
        // TODO: when displaying email, do the reverse replace!


        // Create a new field to be added to the "users" map
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("isTeacher", isTeacher);
        newUser.put("name", username);

        // Add the new field to the "users" map in the document "known-users"
        docRef.update("users." + "" + emailKey, newUser)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "New user field added successfully"))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding new user field", e));
    }
}