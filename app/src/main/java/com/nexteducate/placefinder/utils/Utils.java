package com.nexteducate.placefinder.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Utils {


    public static boolean saveImageToInternalStorage(final Context context, final Bitmap image,String fileName) {

        try {
            final FileOutputStream fos = context.openFileOutput(fileName+".png", Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public static Bitmap loadImageBitmap(final Context context,String name) {

        final FileInputStream fileInputStream;
        Bitmap bitmap = null;
        try {
            fileInputStream = context.openFileInput(name+".png");

            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static boolean fileExist(final Context context,String  name) {
        return context.getFileStreamPath(name).exists();

    }


}

