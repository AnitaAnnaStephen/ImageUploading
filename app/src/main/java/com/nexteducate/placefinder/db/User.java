package com.nexteducate.placefinder.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "place")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "place_type")
    private String place_type;
    @ColumnInfo(name = "place")
    private String place;
    @ColumnInfo(name = "place_title")
    private String place_title;
    @ColumnInfo(name="thumbnail")
    private String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPlace_type() {
        return place_type;
    }

    public String getPlace() {
        return place;
    }

    public String getPlace_title() {
        return place_title;
    }

    public String getPlace_address() {
        return place_address;
    }

    public String getPlace_description() {
        return place_description;
    }

    public String getPlace_latitude() {
        return place_latitude;
    }

    public String getPlace_longitude() {
        return place_longitude;
    }

    @ColumnInfo(name = "place_address")
    private String place_address;
    @ColumnInfo(name = "place_description")
    private String place_description;
    @ColumnInfo(name = "place_latitude")
    private String place_latitude;
    @ColumnInfo(name = "place_longitude")
    private String place_longitude;
    public User(String place_type, String place, String place_title, String place_address, String place_description, String place_latitude, String place_longitude) {
        this.place_type = place_type;
        this.place = place;
        this.place_title = place_title;
        this.place_address = place_address;
        this.place_description = place_description;
        this.place_latitude = place_latitude;
        this.place_longitude = place_longitude;
    }


}
