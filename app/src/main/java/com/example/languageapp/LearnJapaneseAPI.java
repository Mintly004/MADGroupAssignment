package com.example.languageapp;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LearnJapaneseAPI {
    private final String apiKey;
    private final String apiHost;
    private final String apiURL = "https://kanjialive-api.p.rapidapi.com/api/public/";

    String advancedSearch = "search/advanced/?";

    public LearnJapaneseAPI(String apiKey, String apiHost) {
        this.apiKey = apiKey;
        this.apiHost = apiHost;
    }

    public JSONArray makeHttpRequest(String apiFullURL) throws IOException, JSONException {
        URL url = new URL(apiFullURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-RapidAPI-Key", this.apiKey);
        connection.setRequestProperty("X-RapidAPI-Host", this.apiHost);

        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            return new JSONArray(response.toString());

        } else {
            System.out.println("Response failed with response code: " + responseCode);
        }

        connection.disconnect();

        return null;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isNegative(int value) {
        return value < 0;
    }

    /**
     * Retrieves the Onyomi reading for a given string.
     *
     * @param on Required Katakana or romaji.
     * @return The API endpoint URL for advanced search with Onyomi reading.
     * @throws IllegalArgumentException if on is not a valid Katakana or romaji.
     */
    public String OnyomiReading(String on) {
        if (isNullOrEmpty(on)) {
            throw new IllegalArgumentException("Invalid Onyomi reading. Must not be null or empty.");
        }

        String base = "on=";
        return apiURL + advancedSearch + base + on;
    }

    /**
     * Retrieves Kanji details by English meaning.
     *
     * @param kem Required Simplified English kanji meaning.
     * @return The API endpoint URL for advanced search by Kanji English meaning.
     * @throws IllegalArgumentException if kem is null or empty.
     */
    public String KanjiEnglishMeaning(String kem) {
        if (isNullOrEmpty(kem)) {
            throw new IllegalArgumentException("Invalid Kanji English meaning. Must not be null or empty.");
        }

        String base = "kem=";
        return apiURL + advancedSearch + base + kem;
    }

    /**
     * Retrieves Kanji details by stroke number.
     *
     * @param ks Required Positive integer.
     * @return The API endpoint URL for advanced search by Kanji stroke number.
     * @throws IllegalArgumentException if ks is negative.
     */
    public String KanjiStrokeNumber(int ks) {
        if (isNegative(ks)) {
            throw new IllegalArgumentException("Kanji stroke number must be a positive integer.");
        }

        String base = "ks=";
        return apiURL + advancedSearch + base + ks;
    }

    /**
     * Retrieves Kanji details by Radical Japanese Name.
     *
     * @param rjn Required Hiragana or romaji.
     * @return The API endpoint URL for advanced search by Radical Japanese Name.
     * @throws IllegalArgumentException if rjn is not a valid Hiragana or romaji.
     */
    public String RadicalJapaneseName(String rjn) {
        if (isNullOrEmpty(rjn)) {
            throw new IllegalArgumentException("Invalid Radical Japanese Name. Must not be null or empty.");
        }

        String base = "rjn=";
        return apiURL + advancedSearch + base + rjn;
    }

    /**
     * Retrieves Kanji details by Radical English Meaning.
     *
     * @param rem Required Radical's meaning in English.
     * @return The API endpoint URL for advanced search by Radical English Meaning.
     * @throws IllegalArgumentException if rem is null or empty.
     */
    public String RadicalEnglishMeaning(String rem) {
        if (isNullOrEmpty(rem)) {
            throw new IllegalArgumentException("Invalid Radical English Meaning. Must not be null or empty.");
        }

        String base = "rem=";
        return apiURL + advancedSearch + base + rem;
    }

    /**
     * Retrieves Kanji details by Radical Position.
     *
     * @param rpos Required Hiragana or romaji.
     * @return The API endpoint URL for advanced search by Radical Position.
     * @throws IllegalArgumentException if rpos is not a valid Hiragana or romaji.
     */
    public String RadicalPosition(String rpos) {
        if (isNullOrEmpty(rpos)) {
            throw new IllegalArgumentException("Invalid Radical Position. Must not be null or empty.");
        }

        String base = "rpos=";
        return apiURL + advancedSearch + base + rpos;
    }

    /**
     * Retrieves Kanji details by Radical Stroke Number.
     *
     * @param rs Required Positive integer.
     * @return The API endpoint URL for advanced search by Radical Stroke Number.
     * @throws IllegalArgumentException if rs is negative.
     */
    public String RadicalStrokeNumber(int rs) {
        if (isNegative(rs)) {
            throw new IllegalArgumentException("Radical Stroke Number must be a positive integer.");
        }

        String base = "rs=";
        return apiURL + advancedSearch + base + rs;
    }

    /**
     * Retrieves a list of Kanji based on AP Exam.
     *
     * @param list Required Kanji divided into chapters (1-20) by Kanji alive.
     * @return The API endpoint URL for advanced search by AP Exam.
     * @throws IllegalArgumentException if list is null or empty.
     */
    public String AP_Exam(String list) {
        if (isNullOrEmpty(list)) {
            throw new IllegalArgumentException("Invalid AP Exam list. Must not be null or empty.");
        }

        String base = "list=";
        return apiURL + advancedSearch + base + list;
    }

    /**
     * Retrieves Kanji details by Kanji Character.
     *
     * @param kanji Required Kanji Character.
     * @return The API endpoint URL for advanced search by Kanji Character.
     * @throws IllegalArgumentException if kanji is null or empty.
     */
    public String KanjiCharacter(String kanji) {
        if (isNullOrEmpty(kanji)) {
            throw new IllegalArgumentException("Invalid Kanji Character. Must not be null or empty.");
        }

        String base = "kanji=";
        return apiURL + advancedSearch + base + kanji;
    }

    /**
     * Retrieves a list of Kanji based on Macquarie.
     *
     * @param list Required Kanji divided into chapters (12-22)
     * @return The API endpoint URL for advanced search by Macquarie.
     * @throws IllegalArgumentException if list is null or empty.
     */
    public String Macquarie(String list) {
        if (isNullOrEmpty(list)) {
            throw new IllegalArgumentException("Invalid Macquarie list. Must not be null or empty.");
        }

        String base = "list=";
        return apiURL + "search/advanced?" + base + list;
    }

    /**
     * Retrieves Kanji details by Grade Level.
     *
     * @param grade Required Positive integer.
     * @return The API endpoint URL for advanced search by Grade Level.
     * @throws IllegalArgumentException if grade is negative.
     */
    public String KanjiGradeLevel(int grade) {
        if (isNegative(grade)) {
            throw new IllegalArgumentException("Grade Level must be a positive integer.");
        }

        String base = "grade=";
        return apiURL + advancedSearch + base + grade;
    }

    /**
     * Retrieves the API endpoint URL for retrieving details of a single Kanji character.
     *
     * @param kanji The Kanji character to retrieve details for.
     * @return The API endpoint URL for retrieving details of a single Kanji character.
     * @throws IllegalArgumentException if kanji is null or empty.
     */
    public String SingleKanjiDetails(String kanji) {
        if (isNullOrEmpty(kanji)) {
            throw new IllegalArgumentException("Invalid Kanji Character. Must not be null or empty.");
        }

        String base = "kanji/";
        return apiURL + base + kanji;
    }

    /**
     * Retrieves the API endpoint URL for retrieving details of all Kanji characters.
     *
     * @return The API endpoint URL for retrieving details of all Kanji characters.
     */
    public String AllKanjiDetails() {
        return SingleKanjiDetails("all");
    }

    /**
     * Retrieves the API endpoint URL for basic search.
     *
     * @param query Required N.B. With Basic Search, Onyomi and Kunyomi values must be in katakana or hiragana.
     * @return The API endpoint URL for basic search.
     * @throws IllegalArgumentException if query is null or empty.
     */
    public String basicSearch(String query) {
        if (isNullOrEmpty(query)) {
            throw new IllegalArgumentException("Invalid search query. Must not be null or empty.");
        }

        String base = "search/";
        return apiURL + base + query;
    }

}