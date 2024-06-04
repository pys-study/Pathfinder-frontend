package com.example.pathfinder.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.pathfinder.data.AppDatabase;
import com.example.pathfinder.data.dao.MessageDao;
import com.example.pathfinder.data.entity.MessageEntity;
import com.example.pathfinder.dto.MessageDto;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ChatViewModel extends AndroidViewModel {
    private final MessageDao messageDao;
    private final LiveData<List<MessageDto>> allMessages;
    private final ExecutorService executorService;

    public ChatViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        messageDao = db.messageDao();
        executorService = Executors.newSingleThreadExecutor();

        allMessages = Transformations.map(messageDao.getAllMessages(), messageEntities -> {
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
            messageDao.insert(new MessageEntity(content, isMine));
        });
    }

    public void deleteAllMessages() {
        executorService.execute(messageDao::deleteAll);
    }
}
