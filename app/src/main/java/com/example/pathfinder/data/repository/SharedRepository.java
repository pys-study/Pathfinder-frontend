package com.example.pathfinder.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.pathfinder.dto.LocationDto;

import java.util.ArrayList;
import java.util.List;

public class SharedRepository {
    private final MutableLiveData<String> nickname = new MutableLiveData<>();
    private final MutableLiveData<String> response = new MutableLiveData<>();
    private final MutableLiveData<List<LocationDto>> locations = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> country = new MutableLiveData<>();
    private final MutableLiveData<String> period = new MutableLiveData<>();
    private final MutableLiveData<String> who = new MutableLiveData<>();
    private final MutableLiveData<String> style = new MutableLiveData<>();
    private final MutableLiveData<String> schedule = new MutableLiveData<>();
    private final MutableLiveData<String> summary = new MutableLiveData<>();

    public MutableLiveData<String> getNickname() {
        return nickname;
    }

    public MutableLiveData<String> getResponse() {
        return response;
    }

    public MutableLiveData<List<LocationDto>> getLocations() {
        return locations;
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    public MutableLiveData<String> getPeriod() {
        return period;
    }

    public MutableLiveData<String> getWho() {
        return who;
    }

    public MutableLiveData<String> getStyle() {
        return style;
    }

    public MutableLiveData<String> getSchedule() {
        return schedule;
    }

    public MutableLiveData<String> getSummary() {
        return summary;
    }

    public void updateSummary() {
        StringBuilder summaryBuilder = new StringBuilder();
        if (country.getValue() != null) summaryBuilder.append(country.getValue()).append(" ");
        if (period.getValue() != null) summaryBuilder.append(period.getValue()).append(" ");
        if (who.getValue() != null) summaryBuilder.append(who.getValue()).append(" ");
        if (style.getValue() != null) summaryBuilder.append(style.getValue()).append(" ");
        if (schedule.getValue() != null) summaryBuilder.append(schedule.getValue()).append(" ");
        summary.setValue(summaryBuilder.toString().trim());
    }
}
