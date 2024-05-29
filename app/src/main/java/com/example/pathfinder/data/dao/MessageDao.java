package com.example.pathfinder.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pathfinder.data.entity.MessageEntity;

import java.util.List;


// 데이터베이스에 접근하는 메서드를 정의

@Dao
public interface MessageDao {
    @Insert
    void insert(MessageEntity message);

    @Query("SELECT * FROM messages ORDER BY id ASC")
    LiveData<List<MessageEntity>> getAllMessages();
}
