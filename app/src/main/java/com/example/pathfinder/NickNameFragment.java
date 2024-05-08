package com.example.pathfinder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pathfinder.databinding.FragmentNickNameBinding;

public class NickNameFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNickNameBinding binding = FragmentNickNameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}