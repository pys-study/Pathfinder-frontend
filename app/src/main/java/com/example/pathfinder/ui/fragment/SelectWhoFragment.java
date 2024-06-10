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
import com.example.pathfinder.databinding.FragmentSelectWhoBinding;
import com.example.pathfinder.ui.viewmodel.SharedViewModel;

public class SelectWhoFragment extends Fragment {

    private FragmentSelectWhoBinding binding;
    private SharedViewModel sharedViewModel;
    private Button selectedButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectWhoBinding.inflate(inflater, container, false);

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
                binding.btnSolo, binding.btnFriend, binding.btnPartner, binding.btnSpouse,
                binding.btnChild, binding.btnParents, binding.btnOthers
        };

        for (Button button : buttons) {
            button.setOnClickListener(buttonClickListener);
        }

        binding.btnNext.setOnClickListener(v -> {
            if (selectedButton == null) {
                Toast.makeText(getContext(), "메뉴를 선택해 주세요", Toast.LENGTH_SHORT).show();
            }else{

                String buttonText = selectedButton.getText().toString();
                sharedViewModel.setWho(buttonText);
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_selectWhoFragment_to_selectTravelStyle);
            }
        });

        return binding.getRoot();
    }
}
