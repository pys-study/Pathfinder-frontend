package com.example.pathfinder.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> nickname = new MutableLiveData<>();
    private final MutableLiveData<String> selectedTexts = new MutableLiveData<>("");
    private final MutableLiveData<String> response = new MutableLiveData<>();


    public void setNickname(String nickname) {
        this.nickname.setValue(nickname);
    }

    public MutableLiveData<String> getNickname() {
        return nickname;
    }

    public void addText(String text) {
        String currentTexts = selectedTexts.getValue();
        if (currentTexts == null) currentTexts = "";
        selectedTexts.setValue(currentTexts + text + " ");
    }

    public MutableLiveData<String> getSelectedTexts() {
        return selectedTexts;
    }

    public MutableLiveData<String> getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response.setValue(response);
    }

}
