package com.example.pathfinder.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pathfinder.data.AppDatabase;
import com.example.pathfinder.data.dao.MessageDao;
import com.example.pathfinder.data.entity.MessageEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageRepository {
    private final MessageDao messageDao;
    private final LiveData<List<MessageEntity>> allMessages;
    private final ExecutorService executorService;

    public MessageRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        messageDao = db.messageDao();
        allMessages = messageDao.getAllMessages();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<MessageEntity>> getAllMessages() {
        return allMessages;
    }

    public void insert(final MessageEntity message) {
        executorService.execute(() -> messageDao.insert(message));
    }
}
