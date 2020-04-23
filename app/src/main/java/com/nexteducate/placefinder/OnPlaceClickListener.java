package com.nexteducate.placefinder;

import android.widget.ImageView;

public interface OnPlaceClickListener {
    void clickPlace(String description, String address, String latitude, String longitude, String placeName
    , ImageView sharedImageView,String subpath,String transName,String url);
}
