package com.nexteducate.placefinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView = findViewById(R.id.logout);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putBoolean("loginUser", false).apply();
                startActivity(new Intent(ProfileActivity.this,Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//    sharedPreferences.edit().putBoolean("loginAdmin", false).apply();
            }
        });
    }
}
