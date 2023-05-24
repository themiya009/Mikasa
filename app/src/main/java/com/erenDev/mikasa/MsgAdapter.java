package com.erenDev.mikasa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyViewHolder>{
    List<Message> messageList;
    public MsgAdapter(List<Message> messageList){
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_page,null);
        MyViewHolder myViewHolder= new MyViewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Message message = messageList.get(position);
        if(message.getSentBy().equals(Message.Sent_By_Me)){
            holder.leftChat.setVisibility(View.GONE);
            holder.rightChat.setVisibility(View.VISIBLE);
            holder.rightTv.setText(message.getMessage());
        } else {
            holder.leftChat.setVisibility(View.VISIBLE);
            holder.rightChat.setVisibility(View.GONE);
            holder.leftTv.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftChat, rightChat;
        TextView leftTv, rightTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            leftChat = itemView.findViewById(R.id.left_chtv);
            rightChat = itemView.findViewById(R.id.right_chtv);
            leftTv = itemView.findViewById(R.id.left_text_view);
            rightTv = itemView.findViewById(R.id.right_text_view);


        }
    }
}
