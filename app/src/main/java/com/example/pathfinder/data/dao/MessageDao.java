package com.example.pathfinder.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pathfinder.data.entity.MessageEntity;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void insert(MessageEntity message);

    @Query("SELECT * FROM messages ORDER BY id ASC")
    LiveData<List<MessageEntity>> getAllMessages();
}
