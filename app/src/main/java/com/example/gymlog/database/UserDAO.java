package com.example.gymlog.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gymlog.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " ORDER BY username")
    LiveData<User> getAllUsers();


    @Query("DELETE FROM " + GymLogDatabase.USER_TABLE) void deleteAll();

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Query("SELECT * FROM " + GymLogDatabase.USER_TABLE + " WHERE id = :id")
    LiveData<User> getUserByUserId(int id);
}
