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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.pathfinder.R;
import com.example.pathfinder.data.api.ApiClient;
import com.example.pathfinder.data.api.GeminiApi;
import com.example.pathfinder.databinding.FragmentScheduleChoiceBinding;
import com.example.pathfinder.dto.LocationDto;
import com.example.pathfinder.dto.RequestBody;
import com.example.pathfinder.dto.ResponseBody;
import com.example.pathfinder.ui.viewmodel.SharedViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectScheduleChoice extends Fragment {

    private FragmentScheduleChoiceBinding binding;
    private SharedViewModel sharedViewModel;
    private Button selectedButton;
    public static ProgressDialogFragment progressDialog;
    private static final String TAG = "SelectScheduleChoice";

    // API key 값 변수로 분리
    private static final String API_KEY = "AIzaSyBJ4ekJ6v2oFbV6NbWaPyV3BW_ODsl2KWQ";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScheduleChoiceBinding.inflate(inflater, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        progressDialog = ProgressDialogFragment.newInstance();

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
                sharedViewModel.setSchedule(buttonText);

                progressDialog.show(getParentFragmentManager(), "progress");

                sharedViewModel.getSummary().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String selectedTexts) {
                        Log.d("gemini", "Selected Texts: " + selectedTexts);
                        requestLatLon(selectedTexts);
                    }
                });
            }
        });

        return binding.getRoot();
    }

    private void requestLatLon(String selectedTexts) {

        String requestText = selectedTexts + "\n 위 주제를 바탕으로 대표적인 아래의 형식에 맞춰서 여행지의 위도와 경도를 제공해줘.파싱해서 사용할 수 있도록 다른 텍스트는 절대 넣지 말고 아래 형식에 해당하는 JSON 형식만 보내라" +
                "[  {\n" +
                "    \"name\": \"\",\n" +
                "    \"latitude\": ,\n" +
                "    \"longitude\": \n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"\",\n" +
                "    \"latitude\": ,\n" +
                "    \"longitude\": \n" +
                "  },]";

        RequestBody.Content.Part part = new RequestBody.Content.Part(requestText);
        List<RequestBody.Content.Part> parts = new ArrayList<>();
        parts.add(part);
        RequestBody.Content content = new RequestBody.Content(parts);
        List<RequestBody.Content> contents = new ArrayList<>();
        contents.add(content);
        RequestBody requestBody = new RequestBody(contents);

        Gson gson = new Gson();
        String json = gson.toJson(requestBody);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);

        GeminiApi api = ApiClient.getApi();
        Call<ResponseBody> call = api.generateContent(API_KEY, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseBody responseBody = response.body();
                    if (responseBody.candidates != null && !responseBody.candidates.isEmpty() &&
                            responseBody.candidates.get(0).content != null &&
                            responseBody.candidates.get(0).content.parts != null &&
                            !responseBody.candidates.get(0).content.parts.isEmpty()) {
                        String latLonResponse = responseBody.candidates.get(0).content.parts.get(0).text;

                        // 불필요한 문자를 제거합니다.
                        latLonResponse = latLonResponse.replace("```", "").trim();

                        Log.d(TAG, "LatLon Response: " + latLonResponse);
                        // parse latLonResponse and save to ViewModel
                        parseAndSaveLatLon(latLonResponse);
                        requestTravelSchedule(latLonResponse, selectedTexts);
                    } else {
                        Log.e(TAG, "Response body structure is not as expected.");
                        Log.d(TAG, "Full Response: " + gson.toJson(responseBody));
                    }
                } else {
                    Log.e(TAG, "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                // ProgressDialogFragment 숨기기
                if (progressDialog.isVisible()) {
                    progressDialog.dismiss();
                }

                Log.e(TAG, "Request failed", t);
            }
        });
    }

    private void parseAndSaveLatLon(String latLonResponse) {
        // Assuming latLonResponse is a JSON string containing location data
        Gson gson = new Gson();
        try {
            LocationDto[] locations = gson.fromJson(latLonResponse, LocationDto[].class);
            List<LocationDto> locationList = new ArrayList<>();
            for (LocationDto location : locations) {
                Log.d(TAG, "Location: " + location.toString());
                locationList.add(location);
            }
            sharedViewModel.setLocations(locationList);
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse latLonResponse", e);
        }
    }



    private void requestTravelSchedule(String latLonResponse, String selectedTexts) {
        String requestText = latLonResponse + "\n위의 여행지를 중심으로 아래의 주제에 맞춰서 여행 일정을 여행지에 맞춰서 자세히 작성해줘.\n"  + selectedTexts;

        RequestBody.Content.Part part = new RequestBody.Content.Part(requestText);
        List<RequestBody.Content.Part> parts = new ArrayList<>();
        parts.add(part);
        RequestBody.Content content = new RequestBody.Content(parts);
        List<RequestBody.Content> contents = new ArrayList<>();
        contents.add(content);
        RequestBody requestBody = new RequestBody(contents);

        Gson gson = new Gson();
        String json = gson.toJson(requestBody);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.get("application/json; charset=utf-8"), json);

        GeminiApi api = ApiClient.getApi();
        Call<ResponseBody> call = api.generateContent(API_KEY, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ResponseBody responseBody = response.body();
                    if (responseBody.candidates != null && !responseBody.candidates.isEmpty() &&
                            responseBody.candidates.get(0).content != null &&
                            responseBody.candidates.get(0).content.parts != null &&
                            !responseBody.candidates.get(0).content.parts.isEmpty()) {
                        String responseText = responseBody.candidates.get(0).content.parts.get(0).text;
                        Log.d(TAG, "Response: " + responseText);
                        sharedViewModel.setResponse(responseText);
                    } else {
                        Log.e(TAG, "Response body structure is not as expected.");
                        Log.d(TAG, "Full Response: " + gson.toJson(responseBody));
                    }
                } else {
                    Log.e(TAG, "Response error: " + response.code());
                }
                navigateToNextFragment();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                // ProgressDialogFragment 숨기기
                if (progressDialog.isVisible()) {
                    progressDialog.dismiss();
                }

                Log.e(TAG, "Request failed", t);
                navigateToNextFragment();
            }
        });
    }

    private void navigateToNextFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_selectScheduleChoice_to_googleMapFragment);

        // ProgressDialogFragment 숨기기
        if (progressDialog.isVisible()) {
            progressDialog.dismiss();
        }

    }
}
