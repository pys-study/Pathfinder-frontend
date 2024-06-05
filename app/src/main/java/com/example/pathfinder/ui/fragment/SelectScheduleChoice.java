package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pathfinder.R;
import com.example.pathfinder.data.api.ApiClient;
import com.example.pathfinder.data.api.GeminiApi;
import com.example.pathfinder.databinding.FragmentScheduleChoiceBinding;
import com.example.pathfinder.dto.RequestBody;
import com.example.pathfinder.dto.ResponseBody;
import com.example.pathfinder.ui.viewmodel.SharedViewModel;
import com.google.gson.Gson;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SelectScheduleChoice extends Fragment {

    private FragmentScheduleChoiceBinding binding;
    private SharedViewModel sharedViewModel;
    private Button selectedButton;
    private static final String TAG = "SelectScheduleChoice";

    // API key 값 변수로 분리
    private static final String API_KEY = "AIzaSyBJ4ekJ6v2oFbV6NbWaPyV3BW_ODsl2KWQ";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleChoiceBinding.inflate(inflater, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        View.OnClickListener buttonClickListener = v -> {
            if (selectedButton != null) {
                selectedButton.setBackgroundResource(R.drawable.border_select_button);
            }
            selectedButton = (Button) v;
            selectedButton.setBackgroundResource(R.drawable.border_selected_button);
        };

        // Adding all buttons to an array for efficient setting of click listeners
        Button[] buttons = {
                binding.btnActivity, binding.btnNature
        };

        for (Button button : buttons) {
            button.setOnClickListener(buttonClickListener);
        }

        binding.btnNext.setOnClickListener(v -> {
            if (selectedButton == null) {
                Toast.makeText(getContext(), "메뉴를 선택해 주세요", Toast.LENGTH_SHORT).show();
            } else {
                String buttonText = selectedButton.getText().toString();
                sharedViewModel.addText(buttonText);

                sharedViewModel.getSelectedTexts().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String selectedTexts) {
                        Log.d(TAG, "Selected Texts: " + selectedTexts);
                        sendRequestToGemini("그리고 여행일정을 여행지에 맞춰서 자세히 작성해줘. 그리고 마크다운 문법 말고 android TextVIew에 잘 보일 수 있도록 맞춰서 작성해\n"+ selectedTexts);
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private void sendRequestToGemini(String selectedTexts) {
        // RequestBody 객체 생성
        RequestBody.Content.Part part = new RequestBody.Content.Part(selectedTexts);
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
                        sharedViewModel.setResponse(responseText);
                    } else {
                        Log.e(TAG, "Response body structure is not as expected.");
                        // 응답 본문을 로그로 출력
                        Log.d(TAG, "Full Response: " + gson.toJson(responseBody));
                    }
                } else {
                    Log.e(TAG, "Response error: " + response.code());
                }
                navigateToNextFragment();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Request failed", t);
                navigateToNextFragment();
            }
        });

        // API key를 로그로 출력
        Log.d(TAG, "API Key: " + API_KEY);
    }

    private void navigateToNextFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_selectScheduleChoice_to_googleMapFragment);
    }
}
