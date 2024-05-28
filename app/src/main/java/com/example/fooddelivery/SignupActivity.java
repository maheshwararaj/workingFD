package com.example.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword;
    private Button signupButton;
    private TextView loginRedirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();

        // Initialize views
        signupEmail = findViewById(R.id.email);
        signupPassword = findViewById(R.id.password);
        signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();

                // Validate user input
                if (user.isEmpty()){
                    signupEmail.setError("Email cannot be empty");
                    return;
                }
                if (pass.isEmpty()){
                    signupPassword.setError("Password cannot be empty");
                    return;
                }

                // Create user with email and password using Firebase Authentication
                auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign-up successful, display a toast message
                            Toast.makeText(getApplicationContext(), "SignUp Successful", Toast.LENGTH_SHORT).show();
                            // Redirect to the login activity
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            // Sign-up failed, display error message
                            Toast.makeText(getApplicationContext(), "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}