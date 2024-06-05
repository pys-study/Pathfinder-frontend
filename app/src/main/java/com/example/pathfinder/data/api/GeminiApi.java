package com.example.pathfinder.data.api;

import com.example.pathfinder.dto.ResponseBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeminiApi {
    @POST("v1beta/models/gemini-pro:generateContent")
    Call<ResponseBody> generateContent(@Query("key") String apiKey, @Body RequestBody body);
}
