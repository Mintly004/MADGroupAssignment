package com.example.languageapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SupabaseAPI {
    private final String apiKey;
    private final String apiBaseURL = "https://xvqkpkatqvdmhfwvuum.supabase.co/rest/v1/";
    private final String tableName = "User";

    public SupabaseAPI(String apiKey){
        this.apiKey = apiKey;
    }

    public JSONArray getUserByEmail(String email){
        try {
            URL url = new URL(apiBaseURL + tableName + "?email=eq." + email);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", apiKey);
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader  reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null){
                    response.append(line);
                }

                reader.close();
                return new JSONArray(response.toString());
            } else {
                // Handle error
                return null;
            }
        } catch (IOException | JSONException e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertuser(String email, String password, String username, String phoneNo) {
        try {
            URL url = new URL(apiBaseURL + tableName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("apikey", apiKey);
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject userPayload = new JSONObject();
            userPayload.put("email", email);
            userPayload.put("password", password);
            userPayload.put("username", username);
            userPayload.put("phoneNo", phoneNo);

            String jsonInputString = userPayload.toString();

            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_CREATED;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(String currentEmail, String newEmail, String newPassword, String newUsername, String newPhoneNo) {
        try {
            if (newEmail == null && newPassword == null && newUsername == null && newPhoneNo == null) {
                return false;
            }

            JSONArray userArray = getUserByEmail(currentEmail);
            if (userArray.length() == 0) {
                // User not found
                return false;
            }

            JSONObject userObject = userArray.getJSONObject(0);
            String uid = userObject.getString("uid");

            // Update
            URL url = new URL(apiBaseURL + tableName + "?uid=eq." + uid);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PATCH");
            connection.setRequestProperty("apikey", apiKey);
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject userPayload = new JSONObject();
            if (newEmail != null) {
                userPayload.put("email", newEmail);
            }
            if (newPassword != null) {
                userPayload.put("password", newPassword);
            }
            if (newUsername != null) {
                userPayload.put("username", newUsername);
            }
            if (newPhoneNo != null) {
                userPayload.put("phoneNo", newPhoneNo);
            }

            String jsonInputString = userPayload.toString();

            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()){
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
