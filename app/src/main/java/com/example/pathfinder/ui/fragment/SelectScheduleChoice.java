package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pathfinder.databinding.FragmentScheduleChoiceBinding;

public class SelectScheduleChoice extends Fragment {

    private FragmentScheduleChoiceBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleChoiceBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}