package com.example.pathfinder.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.pathfinder.dto.MessageDto;

@Entity(tableName = "messages")
public class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private boolean isMine;

    public MessageEntity(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }


    public MessageDto toDto() {
        return new MessageDto(content, isMine);
    }
}
