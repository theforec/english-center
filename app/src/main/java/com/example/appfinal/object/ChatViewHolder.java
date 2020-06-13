package com.example.appfinal.object;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appfinal.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView SenderMessage, ReceiverMessage;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        SenderMessage = itemView.findViewById(R.id.text_view_sender_message);
        ReceiverMessage = itemView.findViewById(R.id.text_view_receiver_message);
    }
}
