package com.example.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    EditText emailText, passwordText;
    Button loginBtn;

    SupabaseAPI api = new SupabaseAPI(getString(R.string.SUPABASE_KEY));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleBtn = findViewById(R.id.google_btn);
        emailText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginbtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1008);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1008) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    // Sign-in was successful, update the UI
                    updateUI(account);
                } else {
                    Toast.makeText(getApplicationContext(), "Sign-in failed", Toast.LENGTH_SHORT).show();
                }
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void updateUI(GoogleSignInAccount account) {
        navigateToSecondActivity();
    }

    void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(LoginActivity.this, SignOutActivity.class);
        startActivity(intent);
    }

    private class APIThread extends Thread {
        private final String email;
        private final String password;

        public APIThread(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public void run() {
            try {
                JSONArray userArray = api.getUserByEmail(email);

                if (userArray != null) {
                    JSONObject user = userArray.getJSONObject(0);
                    if (password.equals(user.getString("password"))) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Password not correct", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

