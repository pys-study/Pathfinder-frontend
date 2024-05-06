package com.example.pathfinder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pathfinder.databinding.SignUp1Binding;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class SignUpFragment extends Fragment {

    private SignUp1Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SignUp1Binding.inflate(inflater, container, false);

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

        binding.SignUpLayout.setOnClickListener(view -> {
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(getContext())) {
                UserApiClient.getInstance().loginWithKakaoTalk(getContext(), callback);
            } else {
                UserApiClient.getInstance().loginWithKakaoAccount(getContext(), callback);
            }
        });

        return binding.getRoot();
    }

    private void updateKaKaoLoginUi() {
        UserApiClient.getInstance().me((user, throwable) -> {
            if (user != null) {
                Log.println(Log.INFO, "SignUpFragment", "사용자 정보 요청 성공");
            } else {
                Log.e("SignUpFragment", "사용자 정보 요청 실패", throwable);
            }
            return null;
        });
    }
}
