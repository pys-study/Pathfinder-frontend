package com.example.pathfinder.dto;


public class MessageDto {
    private String content;
    private boolean isMine;
    public MessageDto(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}


