package com.example.pathfinder.utils;

import com.example.pathfinder.data.entity.MessageEntity;
import com.example.pathfinder.dto.MessageDto;

import java.util.ArrayList;
import java.util.List;

public class MessageMapper {

    public static MessageDto entityToDto(MessageEntity entity) {
        return new MessageDto(entity.getContent(), entity.isMine());
    }

    public static List<MessageDto> entitiesToDtos(List<MessageEntity> entities) {
        List<MessageDto> dtos = new ArrayList<>();
        for (MessageEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
}
