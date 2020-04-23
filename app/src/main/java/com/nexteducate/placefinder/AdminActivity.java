package com.nexteducate.placefinder;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.nexteducate.placefinder.db.AppDatabase;
import com.nexteducate.placefinder.db.User;
import com.nexteducate.placefinder.utils.Utils;

import java.io.FileNotFoundException;

public class AdminActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int PICK_PHOTO_FOR_AVATAR = 101;
    Button submitButton;
    EditText title, address, desc, latitude, longitude;
    Spinner type, placename;
    Uri selectedImage;
    ImageView locimage,logout;
    ImageButton addImage;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();
        requestStoragePermission();
        setListener();
    }

    private void setListener() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String placeTitle = title.getText().toString().trim();
                String add = address.getText().toString().trim();
                String description = desc.getText().toString().trim();
                String latitudeValue = latitude.getText().toString().trim();
                String lon = longitude.getText().toString().trim();
                String typePlace = type.getSelectedItem().toString().trim();
                String name = placename.getSelectedItem().toString().trim();
                if (!isDataValid(placeTitle, add, description, latitudeValue, lon, typePlace, name) || selectedImage == null) {
                    Toast.makeText(AdminActivity.this, "Please enter valid data", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(typePlace, name, placeTitle, add, description, latitudeValue, lon);
                    saveImage(user, String.valueOf(AppDatabase.getAppDatabase(getApplicationContext()).userDao().countusers()));

                }


            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putBoolean("loginAdmin", false).apply();
                startActivity(new Intent(AdminActivity.this,Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void saveImage(User user, String count) {

        if (Utils.saveImageToInternalStorage(getApplicationContext(), bitmap, placename.getSelectedItem().toString() + count)) {
            user.setThumbnail(String.valueOf(getFileStreamPath(placename.getSelectedItem().toString() + count)));
            Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show();
            AppDatabase.getAppDatabase(getApplicationContext()).userDao().insertAll(user);
            Toast.makeText(getApplicationContext(), "" + AppDatabase.getAppDatabase(getApplicationContext()).userDao().countusers(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isDataValid(String title, String address, String desc, String lat, String longitude, String type, String placeName) {
        return !TextUtils.isEmpty(title) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(desc) &&
                !TextUtils.isEmpty(lat) && !TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(type) && !TextUtils.isEmpty(placeName) &&
                lat.length() <= 6 && longitude.length() <= 6;
    }

    private void init() {
        title = findViewById(R.id.placeTitle);
        address = findViewById(R.id.placeAddress);
        desc = findViewById(R.id.placeDescription);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        type = findViewById(R.id.placeTypeSpinner);
        placename = findViewById(R.id.placeSpinner);
        submitButton = findViewById(R.id.bt_submit);
        addImage = findViewById(R.id.addImage);
        logout=findViewById(R.id.logout);
        locimage = findViewById(R.id.projectImage);
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {

            return;
        }

        //And finally ask for the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, STORAGE_PERMISSION_CODE);
        }
    }

    private void showProgressDialog() {
        progressDialog = ProgressDialog.show(AdminActivity.this, null, null, false, false);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission Denied.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {

                //Display an error
                Toast.makeText(AdminActivity.this, "Failed to pick photo. Please try again", Toast.LENGTH_SHORT).show();
                return;
            }


            try {

                selectedImage = data.getData();
                if (selectedImage != null && getContentResolver() != null) {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                    String path = getPath(selectedImage);
                    if (path != null) {
                        //This is necessary for checking old profile picture and whether user decided to change that which already existed.
                        Glide.with(AdminActivity.this).asBitmap().load(bitmap).into(locimage);
                    }


                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }


    private String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();
            cursor = getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                return path;
            }
        }
        return null;


    }
}
