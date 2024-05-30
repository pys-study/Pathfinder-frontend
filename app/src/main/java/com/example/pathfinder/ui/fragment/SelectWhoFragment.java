package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pathfinder.R;
import com.example.pathfinder.databinding.FragmentSelectWhoBinding;

public class SelectWhoFragment extends Fragment {
    private FragmentSelectWhoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectWhoBinding.inflate(inflater, container, false);

        binding.btnNext.setOnClickListener(v -> {
            NavController navController  = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_selectWhoFragment_to_selectTravelStyle);
        });

        return binding.getRoot();
    }

}


