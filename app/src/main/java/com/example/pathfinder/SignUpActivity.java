
package com.example.pathfinder;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pathfinder.databinding.ActivitySignUpBinding;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());

        // 카카오톡이 설치되어 있는지 확인하는 메서드 , 카카오에서 제공함. 콜백 객체를 이용합.
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (throwable != null) {
                    Log.e("SignUpActivity", "로그인 실패", throwable);
                } else if (oAuthToken != null) {
                    Log.i("SignUpActivity", "로그인 성공 " + oAuthToken.getAccessToken());
                    updateKaKaoLoginUi();
                }
                return null;
            }
        };

        binding.SignUpLayout.setOnClickListener(view -> {
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)) {
                UserApiClient.getInstance().loginWithKakaoTalk(this, callback);
            } else {
                UserApiClient.getInstance().loginWithKakaoAccount(this, callback);
            }
        });

        setContentView(binding.getRoot());
    }

    private void updateKaKaoLoginUi() {
        UserApiClient.getInstance().me((user, throwable) -> {
            if (user != null) {
                Log.println(Log.INFO, "SignUpActivity", "사용자 정보 요청 성공");
            } else {
                Log.e("SignUpActivity", "사용자 정보 요청 실패", throwable);
            }
            return null;
        });
    }
}
