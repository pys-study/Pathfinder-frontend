package com.example.pathfinder.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.pathfinder.data.entity.MessageEntity;
import com.example.pathfinder.data.repository.MessageRepository;
import com.example.pathfinder.dto.MessageDto;
import com.example.pathfinder.utils.MessageMapper;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private final MessageRepository repository;
    private final LiveData<List<MessageDto>> allMessages;

    public ChatViewModel(Application application) {
        super(application);
        repository = new MessageRepository(application);
        allMessages = Transformations.map(repository.getAllMessages(), MessageMapper::entitiesToDtos);
    }

    public LiveData<List<MessageDto>> getAllMessages() {
        return allMessages;
    }

    public void addMessage(String content, boolean isMine) {
        MessageEntity message = new MessageEntity(content, isMine);
        repository.insert(message);
    }

    public void deleteAllMessages() {
        repository.deleteAll();
    }
}
