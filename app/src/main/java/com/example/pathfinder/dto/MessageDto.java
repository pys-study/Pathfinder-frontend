package com.example.pathfinder.dto;


public class MessageDto {
    private String content;
    private boolean isMine;
    private long timestamp; // 추가된 타임스탬프

    public MessageDto(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
        this.timestamp = System.currentTimeMillis(); // 메시지 생성 시 타임스탬프 저장
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }

    public long getTimestamp() {
        return timestamp;
    }
}


