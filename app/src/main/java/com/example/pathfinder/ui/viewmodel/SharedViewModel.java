package com.example.pathfinder.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> nickname = new MutableLiveData<>();

    public void setNickname(String nickname) {
        this.nickname.setValue(nickname);
    }

    public MutableLiveData<String> getNickname() {
        return nickname;
    }
}
