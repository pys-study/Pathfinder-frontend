package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.pathfinder.R;
import com.example.pathfinder.databinding.FragmentPeriodBinding;

public class PeriodFragment extends Fragment {

    private FragmentPeriodBinding binding;
    private NavController navController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPeriodBinding.inflate(inflater, container, false);


        binding.btnNext.setOnClickListener(v -> {
            navController.navigate(R.id.action_periodFragment_to_selectWhoFragment);
        });


        return binding.getRoot();
    }
}