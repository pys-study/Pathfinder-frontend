package com.example.pathfinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pathfinder.databinding.FragmentSelectCountryBinding;

public class SelectCountryFragment extends Fragment {

    private FragmentSelectCountryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectCountryBinding.inflate(inflater, container, false);

        binding.btnNext.setOnClickListener(v->{
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_selectCountryFragment_to_periodFragment);
        });

        return binding.getRoot();
    }
}
