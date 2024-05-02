package com.example.pathfinder;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    private static KakaoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY);
    }
}