package com.nexteducate.placefinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    TextView signIn;
    TextInputEditText userName, password;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFieldsEmpty()) {

                    if (userName.getText().toString().equals("user")) {
                        sharedPreferences.edit().putBoolean("loginUser", true).apply();
                        startActivity(new Intent(Login.this, MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else {
                        sharedPreferences.edit().putBoolean("loginAdmin", true).apply();
                        startActivity(new Intent(Login.this, AdminActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                }


            }
        });
    }
//    sharedPreferences.edit().putBoolean("loginUser", false).apply();
//    sharedPreferences.edit().putBoolean("loginAdmin", false).apply();


    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences != null && sharedPreferences.getBoolean("loginUser", false)) {
            startActivity(new Intent(Login.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        if (sharedPreferences != null && sharedPreferences.getBoolean("loginAdmin", false)) {
            startActivity(new Intent(Login.this, AdminActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

    }

    private void init() {
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signInBtn);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    private boolean checkFieldsEmpty() {
        if (userName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter valid username", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter valid password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
