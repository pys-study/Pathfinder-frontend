package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pathfinder.R;
import com.example.pathfinder.databinding.FragmentSelectCountryBinding;
import com.example.pathfinder.ui.viewmodel.SharedViewModel;

public class SelectCountryFragment extends Fragment {

    private FragmentSelectCountryBinding binding;
    private SharedViewModel sharedViewModel;
    private Button selectedButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectCountryBinding.inflate(inflater, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedButton != null) {
                    selectedButton.setBackgroundResource(R.drawable.border_select_button);
                }
                selectedButton = (Button) v;
                selectedButton.setBackgroundResource(R.drawable.border_selected_button);
            }
        };

        // Adding all buttons to an array for efficient setting of click listeners
        Button[] buttons = {
                binding.japan1, binding.japan2, binding.japan3, binding.japan4, binding.japan5, binding.japan6, binding.japan7,
                binding.china1, binding.china2, binding.china3, binding.china4, binding.china5, binding.china6,
                binding.korea1, binding.korea2, binding.korea3, binding.korea4, binding.korea5, binding.korea6, binding.korea7,
                binding.korea8, binding.korea9, binding.korea10, binding.korea11, binding.korea12
        };

        for (Button button : buttons) {
            button.setOnClickListener(buttonClickListener);
        }


        binding.btnNext.setOnClickListener(v -> {
            if (selectedButton == null) {
                Toast.makeText(getContext(), "메뉴를 선택해 주세요", Toast.LENGTH_SHORT).show();
            } else {

                String buttonText = selectedButton.getText().toString();
                sharedViewModel.addText(buttonText);
                sharedViewModel.setCountry(buttonText);
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_selectCountryFragment_to_periodFragment);
            }
        });

        return binding.getRoot();
    }
}
