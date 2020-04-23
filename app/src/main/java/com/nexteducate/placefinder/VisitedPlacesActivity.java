package com.nexteducate.placefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class VisitedPlacesActivity extends AppCompatActivity {
    private String TAG_IMAGE="image";
    private String TAG_NAME="name";
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visited_places);
        Intent intent=getIntent();
        imageView=findViewById(R.id.locationImage);
        textView=findViewById(R.id.placeName);
        String name=intent.getStringExtra(TAG_NAME);
        textView.setText(name);
        int drawable=intent.getIntExtra(TAG_IMAGE,0);
        Glide.with(this).load(drawable)
                .into(imageView);
    }
}
