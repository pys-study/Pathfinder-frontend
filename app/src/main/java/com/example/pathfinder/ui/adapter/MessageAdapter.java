package com.example.pathfinder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pathfinder.R;
import com.example.pathfinder.dto.MessageDto;

public class MessageAdapter extends ListAdapter<MessageDto, MessageAdapter.MessageViewHolder> {

    public MessageAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<MessageDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<MessageDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull MessageDto oldItem, @NonNull MessageDto newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MessageDto oldItem, @NonNull MessageDto newItem) {
            return oldItem.getContent().equals(newItem.getContent()) && oldItem.isMine() == newItem.isMine(); // 메시지 내용 비교 로직 (수정됨)
        }
    };

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageDto message = getItem(position);
        holder.textViewMessage.setText(message.getContent());

        // 메시지 소유자에 따라 레이아웃 조정
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) holder.textViewMessage.getLayoutParams();
        if (message.isMine()) {
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
            holder.textViewMessage.setBackgroundResource(R.drawable.message_background_right);
        } else {
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            holder.textViewMessage.setBackgroundResource(R.drawable.message_background_left);
        }
        holder.textViewMessage.setLayoutParams(layoutParams);
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }
    }
}
