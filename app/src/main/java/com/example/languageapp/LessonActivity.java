package com.example.languageapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity {

    LearnJapaneseAPI api;
    int grade;
    TextView pageDisplay;
    Button nextBtn, homeBtn, backBtn;
    ScrollView resultScroll;
    LinearLayout resultLayout;
    ArrayList<Grapheme> graphemeArrayList = new ArrayList<>();
    int graphemeIndex = 0;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        grade = getIntent().getIntExtra("grade", 1);
        pageDisplay = findViewById(R.id.lesson_page);
        nextBtn = findViewById(R.id.lesson_next);
        homeBtn = findViewById(R.id.lesson_home);
        backBtn = findViewById(R.id.lesson_back);
        resultScroll = findViewById(R.id.resultScrollView);
        resultScroll.setVisibility(View.GONE);
        resultLayout = findViewById(R.id.resultLayout);
        typeface = ResourcesCompat.getFont(this, R.font.fredoka_one);

        api = new LearnJapaneseAPI(
                getString(R.string.X_RapidAPI_Key),
                getString(R.string.X_RapidAPI_Host)
        );

        // Start
        new APIThread(grade).start();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graphemeIndex <= (graphemeArrayList.size() - 1)) {
                    resultLayout.removeAllViews();
                    createSingleGrapheme(graphemeArrayList.get(++graphemeIndex));
                    updatePageDisplay();
                } else {
                    Toast.makeText(LessonActivity.this, "This is the last page", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (graphemeIndex >= 0) {
                    resultLayout.removeAllViews();
                    createSingleGrapheme(graphemeArrayList.get(--graphemeIndex));
                    updatePageDisplay();
                } else {
                    Toast.makeText(LessonActivity.this, "This is the first page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    // make API request
    private class APIThread extends Thread {
        private int input;

        public APIThread(int input) {
            this.input = input;
        }

        @Override
        public void run() {
            String url = api.KanjiGradeLevel(input);
            try {
                JSONArray response = api.makeHttpRequest(url);
                for (int i = 0; i < response.length(); i++) {
                    // Each response contains multiple keyword in JSONObject type
                    JSONObject keyword = response.getJSONObject(i);

                    // Each keyword contains kanji and radical JSONObject
                    JSONObject kanjiObject = keyword.getJSONObject("kanji");
                    JSONObject radicalObject = keyword.getJSONObject("radical");

                    // Kanji contains character and stroke
                    String kanjiCharacter = kanjiObject.getString("character");
                    int kanjiStroke = kanjiObject.getInt("stroke");

                    // Radical contains character, stroke and order
                    String radicalCharacter = radicalObject.getString("character");
                    int radicalStroke = radicalObject.getInt("stroke");
                    int radicalOrder = radicalObject.getInt("order");

                    Grapheme grapheme = new Grapheme(
                            kanjiCharacter, kanjiStroke, radicalCharacter, radicalStroke, radicalOrder
                    );

                    graphemeArrayList.add(grapheme);

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultScroll.setVisibility(View.VISIBLE);
                        createSingleGrapheme(graphemeArrayList.get(graphemeIndex));
                        updatePageDisplay();
                    }
                });

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    // generate content
    private void createGraphemeLayouts() {
        resultLayout.removeAllViews();
        for (Grapheme grapheme : graphemeArrayList) {
            // Create a horizontal layout for each grapheme
            createSingleGrapheme(grapheme);
        }
    }

    @SuppressLint("ResourceAsColor")
    private LinearLayout createVerticalLayout(String title, String... details) {
        boolean first = true;
        // Create a vertical layout
        LinearLayout verticalLayout = new LinearLayout(this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);


        // Create and add the title TextView
        TextView titleTextView = new TextView(this);
        titleTextView.setText(title);
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setTextSize(40);
        titleTextView.setTextColor(R.color.cyan);
        titleTextView.setTypeface(typeface);
        verticalLayout.addView(titleTextView);

        // Create and add the detail TextViews
        for (String detail : details) {
            TextView detailTextView = new TextView(this);
            detailTextView.setText(detail);
            if (first) {
                detailTextView.setTextSize(60);
                detailTextView.setGravity(Gravity.CENTER);
                first = false;
            } else {
                detailTextView.setTextSize(30);
            }
            detailTextView.setTypeface(typeface);
            verticalLayout.addView(detailTextView);
        }

        return verticalLayout;
    }

    private void createSingleGrapheme(Grapheme grapheme) {
        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Create the first vertical layout for Kanji details
        LinearLayout kanjiLayout = createVerticalLayout(
                "Kanji",
                grapheme.getKanjiCharacter(),
                "stroke: " + grapheme.getKanjiStroke()
        );

        Space emptySpace = new Space(this);
        emptySpace.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.MATCH_PARENT));


        // Create the second vertical layout for Radical details
        LinearLayout radicalLayout = createVerticalLayout(
                "Radical",
                grapheme.getRadicalCharacter(),
                "stroke: " + grapheme.getRadicalStroke(),
                "order: " + grapheme.getRadicalOrder()
        );

        // Add the vertical layouts to the horizontal layout
        horizontalLayout.addView(kanjiLayout);
        horizontalLayout.addView(emptySpace);
        horizontalLayout.addView(radicalLayout);

        // Add the horizontal layout to the result layout
        resultLayout.addView(horizontalLayout);
    }

    private void updatePageDisplay() {
        pageDisplay.setText(graphemeIndex + " / " + graphemeArrayList.size());
    }

}
