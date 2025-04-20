package com.example.gymlog.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gymlog.database.entities.GymLog;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    void insert(GymLog gymlog);

    @Query("SELECT * FROM " + GymLogDatabase.gymLogTable + " ORDER BY date DESC")
    List<GymLog> getAllRecords();



    @Query("SELECT * FROM " + GymLogDatabase.gymLogTable + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    List<GymLog> getRecordsByUserId(int loggedInUserId);
}
