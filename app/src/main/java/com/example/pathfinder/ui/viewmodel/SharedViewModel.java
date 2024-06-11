package com.example.pathfinder.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pathfinder.data.repository.SharedRepository;
import com.example.pathfinder.dto.LocationDto;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final SharedRepository repository;

    public SharedViewModel() {
        repository = new SharedRepository();
    }

    public void setNickname(String nickname) {
        repository.getNickname().setValue(nickname);
    }

    public LiveData<String> getNickname() {
        return repository.getNickname();
    }

    public LiveData<String> getResponse() {
        return repository.getResponse();
    }

    public void setResponse(String response) {
        repository.getResponse().setValue(response);
    }

    public LiveData<List<LocationDto>> getLocations() {
        return repository.getLocations();
    }

    public void setLocations(List<LocationDto> locations) {
        repository.getLocations().setValue(locations);
    }

    public LiveData<String> getCountry() {
        return repository.getCountry();
    }

    public void setCountry(String country) {
        repository.getCountry().setValue(country);
        repository.updateSummary();
    }

    public LiveData<String> getPeriod() {
        return repository.getPeriod();
    }

    public void setPeriod(String period) {
        repository.getPeriod().setValue(period);
        repository.updateSummary();
    }

    public LiveData<String> getWho() {
        return repository.getWho();
    }

    public void setWho(String who) {
        repository.getWho().setValue(who);
        repository.updateSummary();
    }

    public LiveData<String> getStyle() {
        return repository.getStyle();
    }

    public void setStyle(String style) {
        repository.getStyle().setValue(style);
        repository.updateSummary();
    }

    public LiveData<String> getSchedule() {
        return repository.getSchedule();
    }

    public void setSchedule(String schedule) {
        repository.getSchedule().setValue(schedule);
        repository.updateSummary();
    }

    public LiveData<String> getSummary() {
        return repository.getSummary();
    }

    public void setSummary(String summary) {
        repository.getSummary().setValue(summary);
    }
}
