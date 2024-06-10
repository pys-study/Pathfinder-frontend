package com.example.pathfinder.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pathfinder.dto.LocationDto;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> nickname = new MutableLiveData<>();
    private final MutableLiveData<String> selectedTexts = new MutableLiveData<>("");
    private final MutableLiveData<String> response = new MutableLiveData<>();
    private final MutableLiveData<List<LocationDto>> locations = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> country = new MutableLiveData<>();
    private final MutableLiveData<String> period = new MutableLiveData<>();


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
    }

    public MutableLiveData<String> getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period.setValue(period);
    }


//    public void addLocation(LocationDto location) {
//        List<LocationDto> currentLocations = locations.getValue();
//        if (currentLocations == null) currentLocations = new ArrayList<>();
//        currentLocations.add(location);
//        locations.setValue(currentLocations);
//    }
}
