package com.example.pathfinder;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.pathfinder.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);




        // 기본 선택 "여행시작"
        //binding.bottomNavigationView.setSelectedItemId(R.id.startTravelFragment);

        // 네비게이션바 가시성 설정
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Using if-else to check the destination IDs
            if (destination.getId() == R.id.startFragment || destination.getId() == R.id.nickNameFragment) {
                binding.bottomNavigationView.setVisibility(View.GONE);
            } else {
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
        // 메뉴 항목 선택 시의 동작을 정의
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navTravelStart) {
                    navController.navigate(R.id.startTravelFragment);
                    return true;
                } else if (itemId == R.id.navAiChat) {
                    navController.navigate(R.id.aiChatFragment);
                    return true;
                } else if (itemId == R.id.navCommunity) {
                    navController.navigate(R.id.communityFragment);
                    return true;
                } else if (itemId == R.id.navMyPage) {
                    navController.navigate(R.id.myPageFragment);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
