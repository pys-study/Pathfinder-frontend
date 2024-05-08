package com.example.pathfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pathfinder.databinding.FragmentStartBinding;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class StartFragment extends Fragment {

    private FragmentStartBinding binding;
    private NavController navController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        // navController 가져오기
        navController = Navigation.findNavController(view);

        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (throwable != null) {
                    Log.e("SignUpFragment", "로그인 실패", throwable);
                } else if (oAuthToken != null) {
                    Log.i("SignUpFragment", "로그인 성공 " + oAuthToken.getAccessToken());
                    updateKaKaoLoginUi();
                }
                return null;
            }
        };

        binding.SignUpLayout.setOnClickListener(v -> {
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(getContext())) {
                UserApiClient.getInstance().loginWithKakaoTalk(getContext(), callback);
            } else {
                UserApiClient.getInstance().loginWithKakaoAccount(getContext(), callback);
            }
        });

    }

    private void updateKaKaoLoginUi() {
        UserApiClient.getInstance().me((user, throwable) -> {
            if (user != null) {
                Log.println(Log.INFO, "SignUpFragment", "사용자 정보 요청 성공");
                // NickNameFragment로 이동
                navController.navigate(R.id.action_startFragment_to_nickNameFragment);
            } else {
                Log.e("SignUpFragment", "사용자 정보 요청 실패", throwable);
            }
            return null;
        });
    }
}
