package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pathfinder.R;
import com.example.pathfinder.databinding.FragmentTravelStyleBinding;


public class SelectTravelStyle extends Fragment {
    private FragmentTravelStyleBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTravelStyleBinding.inflate(inflater, container, false);

        binding.btnNext.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_selectTravelStyle_to_selectScheduleChoice);
        });



//            // 뒤로 가기 버튼 설정
//            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
//                @Override
//                public void handleOnBackPressed() {
//                    // 뒤로 가기 동작 구현
//                    NavController navController = NavHostFragment.findNavController(SelectTravelStyle.this);
//                    navController.popBackStack(); // 이전 화면으로 돌아가기
//                }
//            });




        return binding.getRoot();
    }

}


