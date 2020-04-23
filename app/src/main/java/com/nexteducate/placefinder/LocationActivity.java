package com.nexteducate.placefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class LocationActivity extends AppCompatActivity {
    AppCompatButton getDirectionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        final Spinner placeType = findViewById(R.id.placeTypeSpinner);
        final Spinner placename = findViewById(R.id.placeNameSpinner);
        getDirectionBtn = findViewById(R.id.getDirection);
        getDirectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placeNameValue = placename.getSelectedItem().toString();
                String placeTypeValue = placeType.getSelectedItem().toString();
                startActivity(new Intent(LocationActivity.this, ViewAllListActivity.class)
                        .putExtra("placeName", placeNameValue).putExtra("placeType", placeTypeValue));
            }
        });

    }
}
