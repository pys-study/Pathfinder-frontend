package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Back button handling
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // ViewModel의 selectedTexts 초기화
                sharedViewModel.getSelectedTexts().setValue("");

                NavController navController = Navigation.findNavController(view);
                // 백스택 초기화하고 startTravelFragment로 이동
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.startTravelFragment, true)
                        .build();
                navController.navigate(R.id.action_googleMapFragment_to_startTravelFragment, null, navOptions);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // 서울의 3군데 여행지
        LatLng place1 = new LatLng(37.5561, 126.9723); // 서울역
        LatLng place2 = new LatLng(37.5796, 126.9770); // 경복궁

        mMap.addMarker(new MarkerOptions().position(place1).title("Seoul"));
        mMap.addMarker(new MarkerOptions().position(place2).title("Gyeongbokgung Palace"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1, 12));

    }
}
