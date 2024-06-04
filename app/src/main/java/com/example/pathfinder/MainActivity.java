package com.example.pathfinder;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.pathfinder.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getKeyHash();


        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);


        // 네비게이션바 가시성 설정
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Using if-else to check the destination IDs
            if (destination.getId() == R.id.startTravelFragment || destination.getId() == R.id.aiChatFragment || destination.getId() == R.id.communityFragment || destination.getId() == R.id.myPageFragment) {
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                binding.bottomNavigationView.setVisibility(View.GONE);
            }
        });

        // Window의 softInputMode를 adjustResize로 설정
        getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


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

    private void getKeyHash() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures)
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
    }


}
