package com.example.pathfinder.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pathfinder.dto.LocationDto;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> nickname = new MutableLiveData<>();
    private final MutableLiveData<String> response = new MutableLiveData<>();
    private final MutableLiveData<List<LocationDto>> locations = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> country = new MutableLiveData<>();
    private final MutableLiveData<String> period = new MutableLiveData<>();
    private final MutableLiveData<String> who = new MutableLiveData<>();
    private final MutableLiveData<String> style = new MutableLiveData<>();
    private final MutableLiveData<String> schedule = new MutableLiveData<>();
    private final MutableLiveData<String> summary = new MutableLiveData<>();

    public void setNickname(String nickname) {
        this.nickname.setValue(nickname);
    }

    public MutableLiveData<String> getNickname() {
        return nickname;
    }

    public MutableLiveData<String> getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response.setValue(response);
    }

    public MutableLiveData<List<LocationDto>> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDto> locations) {
        this.locations.setValue(locations);
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country.setValue(country);
        updateSummary();
    }

    public MutableLiveData<String> getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period.setValue(period);
        updateSummary();
    }

    public MutableLiveData<String> getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who.setValue(who);
        updateSummary();
    }

    public MutableLiveData<String> getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style.setValue(style);
        updateSummary();
    }

    public MutableLiveData<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule.setValue(schedule);
        updateSummary();
    }

    public MutableLiveData<String> getSummary() {
        return summary;
    }

    private void updateSummary() {
        StringBuilder summaryBuilder = new StringBuilder();
        if (country.getValue() != null) summaryBuilder.append(country.getValue()).append(" ");
        if (period.getValue() != null) summaryBuilder.append(period.getValue()).append(" ");
        if (who.getValue() != null) summaryBuilder.append(who.getValue()).append(" ");
        if (style.getValue() != null) summaryBuilder.append(style.getValue()).append(" ");
        if (schedule.getValue() != null) summaryBuilder.append(schedule.getValue()).append(" ");
        summary.setValue(summaryBuilder.toString().trim());
    }
}
