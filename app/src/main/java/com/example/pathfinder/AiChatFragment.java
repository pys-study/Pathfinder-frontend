package com.example.pathfinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pathfinder.databinding.FragmentAiChatBinding;
import com.example.pathfinder.dto.MessageDto;
import com.example.pathfinder.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiChatFragment extends Fragment {
    private FragmentAiChatBinding binding;
    private MessageAdapter adapter;
    private String[] sampleResponses = {
            "안녕하세요! 무엇을 도와드릴까요?",
            "오늘 날씨가 참 좋네요.",
            "그렇게 생각하시는군요.",
            "좀 더 자세히 설명해 주실 수 있나요?",
            "저도 그렇게 생각합니다."
    };
    private Random random = new Random();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAiChatBinding.inflate(inflater, container, false);
        setupChat();
        return binding.getRoot();
    }

    private void setupChat() {
        adapter = new MessageAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);  // 레이아웃 매니저 설정
        binding.recyclerView.setAdapter(adapter);

        binding.sendButton.setOnClickListener(v -> {
            String userMessage = binding.editTextMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                List<MessageDto> currentList = new ArrayList<>(adapter.getCurrentList());
                currentList.add(new MessageDto(userMessage, true));

                String response = sampleResponses[random.nextInt(sampleResponses.length)];
                currentList.add(new MessageDto(response, false));

                // 한번에 모든 메시지를 추가한 후 submitList를 호출합니다.
                adapter.submitList(new ArrayList<>(currentList));

                binding.editTextMessage.setText("");
                binding.recyclerView.scrollToPosition(currentList.size() - 1);
            }
        });
    }
}
