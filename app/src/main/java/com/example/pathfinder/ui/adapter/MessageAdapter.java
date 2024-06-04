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
import com.example.pathfinder.databinding.FragmentAiChatBinding;
import com.example.pathfinder.databinding.MessageItemBinding;
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
        // 뷰 바인딩 초기화
        MessageItemBinding binding = MessageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MessageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        // 현재 위치의 메시지 데이터를 가져옵니다.
        MessageDto message = getItem(position);

        // 메시지 내용을 텍스트뷰에 설정합니다.
        holder.binding.textViewMessage.setText(message.getContent());

        // 텍스트뷰의 레이아웃 파라미터를 가져옵니다.
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) holder.binding.textViewMessage.getLayoutParams();

        // 텍스트 색상 및 아이콘 기본값 초기화
        holder.binding.textViewMessage.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.white));
        holder.binding.messageIcon.setVisibility(View.GONE);

        if (message.isMine()) {  // 메시지가 사용자가 보낸 것이라면
            // 텍스트뷰를 오른쪽에 정렬합니다.
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
            // 배경을 오른쪽 메시지용으로 설정합니다.
            holder.binding.textViewMessage.setBackgroundResource(R.drawable.message_background_right);


        } else {  // 메시지가 상대방이 보낸 것이라면
            // 텍스트뷰를 왼쪽에 정렬합니다.
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            // 배경을 왼쪽 메시지용으로 설정합니다.
            holder.binding.textViewMessage.setBackgroundResource(R.drawable.message_background_left);
            holder.binding.textViewMessage.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.darker_gray));
            holder.binding.messageIcon.setVisibility(View.VISIBLE);
        }

        // 변경된 레이아웃 파라미터를 텍스트뷰에 적용합니다.
        holder.binding.textViewMessage.setLayoutParams(layoutParams);
    }



    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private final MessageItemBinding binding;

        public MessageViewHolder(@NonNull MessageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
