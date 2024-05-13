package com.example.pathfinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pathfinder.databinding.FragmentNickNameBinding;

public class NickNameFragment extends Fragment {
    private FragmentNickNameBinding binding;
    private SharedViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNickNameBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        viewModel.getNickname().observe(getViewLifecycleOwner(), nickname -> {
            // 여기서 닉네임을 사용하세요
            binding.etNickName.setText(nickname);
        });

        return binding.getRoot();
    }
}