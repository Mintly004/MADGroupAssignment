package com.example.languageapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LessonMenuActivity extends AppCompatActivity {

    int grade = 0;
    Button grade1, grade2, grade3, grade4, grade5, grade6, profileBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_menu);

        grade1 = findViewById(R.id.grade1);
        grade2 = findViewById(R.id.grade2);
        grade3 = findViewById(R.id.grade3);
        grade4 = findViewById(R.id.grade4);
        grade5 = findViewById(R.id.grade5);
        grade6 = findViewById(R.id.grade6);
        profileBtn=findViewById(R.id.profileButton);

        // Set OnClickListener for grade buttons
        grade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = 1;
                startLessonActivity();
            }
        });

        grade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = 2;
                startLessonActivity();
            }
        });

        grade3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = 3;
                startLessonActivity();
            }
        });

        grade4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = 4;
                startLessonActivity();
            }
        });

        grade5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = 5;
                startLessonActivity();
            }
        });

        grade6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade = 6;
                startLessonActivity();
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProfile();
            }
        });
    }

    // Helper method to start LessonActivity with grade value
    private void startLessonActivity() {
        Intent intent = new Intent(LessonMenuActivity.this, LessonActivity.class);
        intent.putExtra("grade", grade);
        startActivity(intent);
    }
    private void startProfile() {
        Intent intent = new Intent(LessonMenuActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
