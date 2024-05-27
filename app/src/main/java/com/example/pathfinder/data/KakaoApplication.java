package com.example.pathfinder.data;

import android.app.Application;

import com.example.pathfinder.BuildConfig;
import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    private static KakaoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, BuildConfig.kakao_native_app_key);
    }
}