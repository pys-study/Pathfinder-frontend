package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pathfinder.R;
import com.example.pathfinder.databinding.FragmentGoogleMapBinding;
import com.example.pathfinder.ui.viewmodel.SharedViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentGoogleMapBinding binding;
    private SharedViewModel sharedViewModel;
    private TextView responseTextView;
    private static final String TAG = "GoogleMapFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGoogleMapBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        responseTextView = binding.getRoot().findViewById(R.id.response_text);

        // SupportMapFragment를 가져와서 맵이 준비되면 콜백을 받도록 설정
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        sharedViewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String response) {
                Log.d(TAG, "Response: " + response);
                responseTextView.setText(response);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // 서울의 3군데 여행지
        LatLng place1 = new LatLng(35.1595, 129.0908); // Example coordinates
//        LatLng place2 = new LatLng(37.5665, 126.9780); // Seoul, South Korea
//        LatLng place3 = new LatLng(35.1796, 129.0756); // Busan, South Korea

        mMap.addMarker(new MarkerOptions().position(place1).title("Place 1"));
//        mMap.addMarker(new MarkerOptions().position(place2).title("Place 2"));
//        mMap.addMarker(new MarkerOptions().position(place3).title("Place 3"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, 12));
    }




}
