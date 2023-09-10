package com.example.languageapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ProfileActivity extends AppCompatActivity {

    Button usernameBtn, phoneBtn, emailBtn, passswordBtn;

    private LinearLayout containerLayout;

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        usernameBtn = findViewById(R.id.changeUsername);
        phoneBtn = findViewById(R.id.changePhoneNumber);
        emailBtn = findViewById(R.id.changeEmail);
        passswordBtn = findViewById(R.id.changePassword);
        containerLayout = findViewById(R.id.container_layout);
        Button sb = findViewById(R.id.submitButton);

        usernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupText("Username");
                containerLayout.setVisibility(View.VISIBLE);
                sb.setVisibility(View.VISIBLE);
            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupText("Phone Number");
                containerLayout.setVisibility(View.VISIBLE);
                sb.setVisibility(View.VISIBLE);
            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupText("Email");
                containerLayout.setVisibility(View.VISIBLE);
                sb.setVisibility(View.VISIBLE);
            }
        });

        passswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupText("Password");
                containerLayout.setVisibility(View.VISIBLE);
                sb.setVisibility(View.VISIBLE);
            }
        });


        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public void popupText(String n) {

        LinearLayout linearLayout = new LinearLayout(ProfileActivity.this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        // Create a new EditText
        EditText editText = new EditText(ProfileActivity.this);

        // Set attributes for the EditText (e.g., width, height, hint)
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editText.setLayoutParams(params);
        params.setMargins(0, 100, 0, 0);

        editText.setHint("  " + n);
        editText.setBackgroundColor(R.color.grey);
        editText.setTextSize(25);
        editText.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.fredoka_one));

        // Add the EditText to the LinearLayout
        linearLayout.addView(editText);
        // Add the LinearLayout to the ConstraintLayout
        containerLayout.addView(linearLayout);
    }
}