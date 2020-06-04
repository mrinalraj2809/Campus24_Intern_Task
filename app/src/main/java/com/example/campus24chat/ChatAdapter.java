package com.example.campus24chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    ArrayList<ChatList> chatLists;
    Context context;
    public ChatAdapter(Context c,ArrayList<ChatList> g)
    {
        chatLists=g;
        context=c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chats_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.chatName.setText(chatLists.get(position).getChats_name());
        holder.lastMessage.setText(chatLists.get(position).getLast_message());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Messenger.class);
                intent.putExtra("CHAT_NAME",chatLists.get(position).getChats_name());
                intent.putExtra("CHAT_KEY",chatLists.get(position).getChat_id());
                intent.putExtra("CHAT_TYPE",chatLists.get(position).getChat_type());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView chatName;
//        TextView timeStamp;
        TextView lastMessage;
        ImageView chatIcon;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chatName = itemView.findViewById(R.id.name_of_group);
            chatIcon = itemView.findViewById(R.id.imagegroupIcon);
            lastMessage = itemView.findViewById(R.id.msg_last_message);
//            timeStamp = itemView.findViewById(R.id.msgdateTime);


//            timeStamp = itemView.findViewById(R.id.textTimeStamp);
        }

    }
}

