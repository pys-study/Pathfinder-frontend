package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pathfinder.data.api.ApiClient;
import com.example.pathfinder.data.api.GeminiApi;
import com.example.pathfinder.databinding.FragmentAiChatBinding;
import com.example.pathfinder.dto.RequestBody;
import com.example.pathfinder.dto.ResponseBody;
import com.example.pathfinder.ui.adapter.MessageAdapter;
import com.example.pathfinder.ui.viewmodel.ChatViewModel;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AiChatFragment extends Fragment {
    private FragmentAiChatBinding binding;
    private MessageAdapter adapter;
    private ChatViewModel chatViewModel;
    private Random random = new Random();
    private static final String TAG = "AiChatFragment";

    // API key 값 변수로 분리
    private static final String API_KEY = "AIzaSyBJ4ekJ6v2oFbV6NbWaPyV3BW_ODsl2KWQ";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAiChatBinding.inflate(inflater, container, false);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        setupChat();
        return binding.getRoot();
    }

    private void setupChat() {
        adapter = new MessageAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        chatViewModel.getAllMessages().observe(getViewLifecycleOwner(), messages -> {
            adapter.submitList(new ArrayList<>(messages));
            binding.recyclerView.post(() -> binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1));
        });

        binding.sendButton.setOnClickListener(v -> {
            String userMessage = binding.editTextMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                chatViewModel.addMessage(userMessage, true);

                // RequestBody 객체 생성
                RequestBody.Content.Part part = new RequestBody.Content.Part(userMessage);
                List<RequestBody.Content.Part> parts = new ArrayList<>();
                parts.add(part);
                RequestBody.Content content = new RequestBody.Content(parts);
                List<RequestBody.Content> contents = new ArrayList<>();
                contents.add(content);
                RequestBody requestBody = new RequestBody(contents);

                // RequestBody 객체를 JSON으로 변환
                Gson gson = new Gson();
                String json = gson.toJson(requestBody);
                okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);

                // Retrofit API 호출
                GeminiApi api = ApiClient.getApi();
                Call<ResponseBody> call = api.generateContent(API_KEY, body);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // 응답이 null이 아닌지 확인
                            ResponseBody responseBody = response.body();
                            if (responseBody.candidates != null && !responseBody.candidates.isEmpty() &&
                                    responseBody.candidates.get(0).content != null &&
                                    responseBody.candidates.get(0).content.parts != null &&
                                    !responseBody.candidates.get(0).content.parts.isEmpty()) {
                                // 서버로부터 받은 응답 처리
                                String responseText = responseBody.candidates.get(0).content.parts.get(0).text;
                                Log.d(TAG, "Response: " + responseText);
                                chatViewModel.addMessage(responseText, false);

                                binding.editTextMessage.setText("");
                                binding.recyclerView.post(() -> binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1));
                            } else {
                                Log.e(TAG, "Response body structure is not as expected.");
                                // 응답 본문을 로그로 출력
                                Log.d(TAG, "Full Response: " + gson.toJson(responseBody));
                            }
                        } else {
                            Log.e(TAG, "Response error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "Request failed", t);
                    }
                });

                // API key를 로그로 출력
                Log.d(TAG, "API Key: " + API_KEY);
            }
        });

        binding.btnRetry.setOnClickListener(v -> {
            chatViewModel.deleteAllMessages();
            binding.recyclerView.postDelayed(() -> {
                binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }, 100); // 초기화 후 지연 스크롤
        });

        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            binding.getRoot().getWindowVisibleDisplayFrame(r);
            int screenHeight = binding.getRoot().getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;
            if (keypadHeight > screenHeight * 0.15) {
                binding.recyclerView.post(() -> binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1));
            }
        });
    }
}
