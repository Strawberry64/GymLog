package com.example.gymlog.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gymlog.Database.entities.GymLog;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    void insert(GymLog gymlog);

    @Query("SELECT * FROM " + GymLogDatabase.gymLogTable)
    ArrayList<GymLog> getAllRecords();
}
