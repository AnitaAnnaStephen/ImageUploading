package com.nexteducate.placefinder.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    //    @Query("Select * from place")
    @Query("SELECT * from place  where place_type like:place_type AND place like :place")
    LiveData<List<User>> getAll(String place_type, String place);

    //    User findUserByName(String firstName, String secondName);
    @Query("SELECT COUNT(*) from place")
    int countusers();

    @Insert
    void insertAll(User... users);
//    @Delete
//    void delete(User user);

}
