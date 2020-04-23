package com.nexteducate.placefinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nexteducate.placefinder.db.AppDatabase;
import com.nexteducate.placefinder.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewAllListActivity extends AppCompatActivity implements OnPlaceClickListener {


    RecyclerView recyclerView;
    String placeName, placeType;
    PlaceAdapter placeAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewalllist);
        init();
        getIntents();
    }

    private void getIntents() {
        Intent intent = getIntent();
        placeName = intent.getStringExtra("placeName").trim();
        placeType = intent.getStringExtra("placeType").trim();
        getData();

    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(ViewAllListActivity.this, "Please wait", "Loading", false, false);
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.show();
        }


    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }


    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        placeAdapter = new PlaceAdapter(getApplicationContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(placeAdapter);



    }


    private void getData() {
        showProgressDialog();
        Log.d("count",placeType+" "+placeName);

        AppDatabase.getAppDatabase(getApplicationContext()).userDao().getAll(placeType,placeName)
                .observe(this, new Observer<List<User>>() {

                    @Override
                    public void onChanged(List<User> users) {

                        hideProgressDialog();
                        Log.d("count",""+AppDatabase.getAppDatabase(getApplicationContext()).userDao().countusers());
                        Log.d("count",""+users.size());

//                        Toast.makeText(ViewAllListActivity.this, "", Toast.LENGTH_SHORT).show();
                        placeAdapter.setUsersArrayList((ArrayList<User>) users,placeType);
                    }
                });


    }

    @Override
    public void clickPlace(String description, String address, String latitude, String longitude, String placeName
    , ImageView sharedImageView,String subpath,String transName,String url) {
        Intent intent=new Intent(ViewAllListActivity.this,DetailActivity.class);
        intent.putExtra("description",description);
        intent.putExtra("address",address);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        intent.putExtra("placeName",placeName);
        intent.putExtra("subpath",subpath);
        intent.putExtra("transName",transName);
        intent.putExtra("url",url);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                Objects.requireNonNull(ViewCompat.getTransitionName(sharedImageView)));

        startActivity(intent, options.toBundle());


    }
}
