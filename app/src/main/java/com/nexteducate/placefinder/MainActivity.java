package com.nexteducate.placefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private LinearLayout recommendone, recommedtwo, visitedone, visitedtwo, visitedthree, topratedone, topratedtwo;
    private EditText etSearch;
    private String TAG_IMAGE = "image";
    private String TAG_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recommendone = findViewById(R.id.imagerecommend1);
        recommedtwo = findViewById(R.id.imagerecommend2);
        visitedone = findViewById(R.id.visited1);
        visitedtwo = findViewById(R.id.visited2);
        visitedthree = findViewById(R.id.visited3);
        topratedone = findViewById(R.id.toprated1);
        topratedtwo = findViewById(R.id.toprated2);
        etSearch = findViewById(R.id.et_search);
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, LocationActivity.class)
                );
            }
        });
        recommedtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, RecomendationActivity.class)
                        .putExtra(TAG_NAME, "Aliquam").putExtra(TAG_IMAGE, R.drawable.hoteltwo));
            }
        });
        recommendone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecomendationActivity.class)
                        .putExtra(TAG_NAME, "Mauris sagittis non elit").putExtra(TAG_IMAGE, R.drawable.hotelone));
            }
        });
        visitedone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VisitedPlacesActivity.class)
                        .putExtra(TAG_NAME, "Curabitur tempus").putExtra(TAG_IMAGE, R.drawable.vone));
            }
        });

        visitedtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VisitedPlacesActivity.class)
                        .putExtra(TAG_NAME, "Quisque").putExtra(TAG_IMAGE, R.drawable.vtwo));
            }
        });

        visitedthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VisitedPlacesActivity.class)
                        .putExtra(TAG_NAME, "Aliquam ac elit").putExtra(TAG_IMAGE, R.drawable.vthree));
            }
        });
        topratedone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TopRatedActivity.class)
                        .putExtra(TAG_NAME, "Suspendisse ornare").putExtra(TAG_IMAGE, R.drawable.gymone));
            }
        });
        topratedtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TopRatedActivity.class)
                        .putExtra(TAG_NAME, "Placerat vel ipsum").putExtra(TAG_IMAGE, R.drawable.resthree));
            }
        });


        initComponent();
    }

    private void initComponent() {


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:

                        return true;
                    case R.id.navigation_search:

                        startActivity(new Intent(MainActivity.this, LocationActivity.class));

                        return true;
                    case R.id.navigation_profile:

                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));

                        return true;
                }
                return false;
            }
        });


    }


}
