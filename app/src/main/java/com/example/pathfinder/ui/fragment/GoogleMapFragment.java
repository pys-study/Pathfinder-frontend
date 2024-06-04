package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pathfinder.R;
import com.example.pathfinder.data.api.DirectionsApiService;
import com.example.pathfinder.data.api.RetrofitClient;
import com.example.pathfinder.databinding.FramgentGoogleMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FramgentGoogleMapBinding binding;
    private TextView durationTextView;
    private TextView distanceTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FramgentGoogleMapBinding.inflate(inflater, container, false);

        durationTextView = binding.getRoot().findViewById(R.id.duration);
        distanceTextView = binding.getRoot().findViewById(R.id.distance);

        // SupportMapFragment를 가져와서 맵이 준비되면 콜백을 받도록 설정
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return binding.getRoot();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // 서울의 3군데 여행지
        LatLng place1 = new LatLng(37.5665, 126.9780); // Example coordinates
        LatLng place2 = new LatLng(37.5700, 126.9820); // Example coordinates
        LatLng place3 = new LatLng(37.5740, 126.9880); // Example coordinates

        mMap.addMarker(new MarkerOptions().position(place1).title("Place 1"));
        mMap.addMarker(new MarkerOptions().position(place2).title("Place 2"));
        mMap.addMarker(new MarkerOptions().position(place3).title("Place 3"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, 12));

        // Directions API 호출
        getRoute(place1, place2, place3);
    }

    private void getRoute(LatLng... places) {
        String apiKey = "AIzaSyBnb8SD28OklxAyKVowSk7iqGEpGX1SGh4";
        StringBuilder waypoints = new StringBuilder();
        for (int i = 1; i < places.length - 1; i++) {
            waypoints.append(places[i].latitude).append(",").append(places[i].longitude).append("|");
        }

        String origin = places[0].latitude + "," + places[0].longitude;
        String destination = places[places.length - 1].latitude + "," + places[places.length - 1].longitude;
        String departureTime = "now";

        DirectionsApiService apiService = RetrofitClient.getClient("https://maps.googleapis.com/").create(DirectionsApiService.class);
        Call<DirectionsResponse> call = apiService.getDirections(origin, destination, waypoints.toString(), apiKey, departureTime);

        call.enqueue(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DirectionsResponse directionsResponse = response.body();
                    if (!directionsResponse.routes.isEmpty()) {
                        DirectionsResponse.Route route = directionsResponse.routes.get(0);
                        List<LatLng> path = PolyUtil.decode(route.overviewPolyline.points);

                        PolylineOptions polylineOptions = new PolylineOptions().addAll(path);
                        mMap.addPolyline(polylineOptions);

                        durationTextView.setText("Duration: " + route.legs.get(0).duration.text);
                        distanceTextView.setText("Distance: " + route.legs.get(0).distance.text);
                    }
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static class DirectionsResponse {
        public List<Route> routes;

        public static class Route {
            public OverviewPolyline overviewPolyline;
            public List<Leg> legs;

            public static class OverviewPolyline {
                public String points;
            }

            public static class Leg {
                public Distance distance;
                public Duration duration;

                public static class Distance {
                    public String text;
                }

                public static class Duration {
                    public String text;
                }
            }
        }
    }
}
