package com.nexteducate.placefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nexteducate.placefinder.utils.Tools;
import com.nexteducate.placefinder.utils.Utils;

public class DetailActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private TextView placename,description,address,coordinates;
    private long latitudeValue,longitudeValue;
    private ImageView locationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        getData();

        initMapFragment();
    }

    private void init() {
        placename=findViewById(R.id.placeName);
        description=findViewById(R.id.placeDescription);
        address=findViewById(R.id.placeAddress);
        coordinates=findViewById(R.id.coordinates);
        locationImage=findViewById(R.id.locationImage);

    }

    private void getData() {
        Intent intent=getIntent();
        String addressintent=intent.getStringExtra("address");
        String descintent=intent.getStringExtra("description");
        String placeNameIntent=intent.getStringExtra("placeName");
        String latitudeintent=intent.getStringExtra("latitude");
        String longitudeintent=intent.getStringExtra("longitude");
        address.setText(addressintent);
        description.setText(descintent);
        placename.setText(placeNameIntent);
        coordinates.setText(String.format(getString(R.string.coordinates),Integer.parseInt(latitudeintent),Integer.parseInt(longitudeintent)));
        latitudeValue=Long.parseLong(latitudeintent);
        longitudeValue=Long.parseLong(longitudeintent);
        String imageTransName=intent.getStringExtra("transName");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            locationImage.setTransitionName(imageTransName);
        }
        String  url=intent.getStringExtra("url");
        Glide.with(getApplicationContext()).load(Utils.loadImageBitmap(getApplicationContext(), url.trim())).into(locationImage);


    }

    private void initMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = Tools.configActivityMaps(googleMap);
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(latitudeValue, longitudeValue));
                mMap.addMarker(markerOptions);
                mMap.moveCamera(zoomingLocation());
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        try {
                            mMap.animateCamera(zoomingLocation());
                        } catch (Exception ignored) {
                        }
                        return true;
                    }
                });
            }
        });
    }

    private CameraUpdate zoomingLocation() {
        return CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeValue, longitudeValue), 13);
    }
}
