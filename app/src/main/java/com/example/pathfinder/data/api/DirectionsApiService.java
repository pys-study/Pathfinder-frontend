package com.example.pathfinder.data.api;

import com.example.pathfinder.ui.fragment.GoogleMapFragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionsApiService {
    @GET("maps/api/directions/json")
    Call<GoogleMapFragment.DirectionsResponse> getDirections(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("waypoints") String waypoints,
            @Query("key") String apiKey,
            @Query("departure_time") String departureTime
    );
}