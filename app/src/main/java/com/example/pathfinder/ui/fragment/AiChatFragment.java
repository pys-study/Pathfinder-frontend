package com.example.pathfinder.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pathfinder.R;
import com.example.pathfinder.databinding.FragmentAiChatBinding;
import com.example.pathfinder.ui.adapter.MessageAdapter;
import com.example.pathfinder.ui.viewmodel.ChatViewModel;

import java.util.ArrayList;
import java.util.Random;

public class AiChatFragment extends Fragment {
    private FragmentAiChatBinding binding;
    private MessageAdapter adapter;
    private ChatViewModel chatViewModel;
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
            binding.recyclerView.scrollToPosition(messages.size() - 1);
        });

        binding.sendButton.setOnClickListener(v -> {
            String userMessage = binding.editTextMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                chatViewModel.addMessage(userMessage, true);

                String response = sampleResponses[random.nextInt(sampleResponses.length)];
                chatViewModel.addMessage(response, false);

                binding.editTextMessage.setText("");
            }
        });

        binding.btnRetry.setOnClickListener(v -> {
            chatViewModel.deleteAllMessages();
        });

    }
}
