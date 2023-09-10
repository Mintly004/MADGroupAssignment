package com.example.languageapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText emailText;
    private EditText passwordText;
    private Button buttonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameText = findViewById(R.id.signupUsername);
        emailText = findViewById(R.id.signupEmail);
        passwordText = findViewById(R.id.signupPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String password =passwordText.getText().toString().trim();

                // Implement your sign-up logic here
                // For demonstration purposes, display a toast message
                Toast.makeText(SignUpActivity.this, "Sign up clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class APIThread extends Thread {
    }
}