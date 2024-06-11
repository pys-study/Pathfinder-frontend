package com.example.pathfinder.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.pathfinder.data.entity.MessageEntity;
import com.example.pathfinder.data.repository.MessageRepository;
import com.example.pathfinder.dto.MessageDto;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ChatViewModel extends AndroidViewModel {
    private final MessageRepository messageRepository;
    private final LiveData<List<MessageDto>> allMessages;
    private final ExecutorService executorService;

    public ChatViewModel(Application application) {
        super(application);
        messageRepository = new MessageRepository(application);
        executorService = Executors.newSingleThreadExecutor();

        allMessages = Transformations.map(messageRepository.getAllMessages(), messageEntities -> {
            return messageEntities.stream()
                    .map(MessageEntity::toDto)
                    .collect(Collectors.toList());
        });
    }

    public LiveData<List<MessageDto>> getAllMessages() {
        return allMessages;
    }

    public void addMessage(String content, boolean isMine) {
        executorService.execute(() -> {
            messageRepository.insert(new MessageEntity(content, isMine));
        });
    }

    public void deleteAllMessages() {
        executorService.execute(messageRepository::deleteAll);
    }
}
