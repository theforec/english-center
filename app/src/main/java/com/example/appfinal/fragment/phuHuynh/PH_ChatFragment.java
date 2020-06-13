package com.example.appfinal.fragment.phuHuynh;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appfinal.R;
import com.example.appfinal.dao.GiaoVienDAO;
import com.example.appfinal.object.ChatItem;
import com.example.appfinal.object.ChatViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PH_ChatFragment extends Fragment {
    @Nullable
    private Button button_send;
    private EditText edit_text_chat;
    private RecyclerView listChat;
    private String ten_gvcn, ten_lop, ten_hs;
    private String sender, receiver;
    private DatabaseReference myRef;

    public static Handler handler;


    public void setSender(String ten, String mail, String lop) {
        this.ten_hs = ten;
        this.sender = mail;
        this.ten_lop = lop;
    }

    public void setGvcn(String tenGV) {
        this.ten_gvcn = tenGV;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_ph_fragment_chat, container, false);
        init(view);
        setUpViewChat();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == GiaoVienDAO.GET_EMAIL_GIAOVIEN) {
                    receiver = msg.getData().getString("email_gvcn");
                }
                return false;
            }
        });

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitChatContent();
                edit_text_chat.setText("");
            }
        });
        return view;
    }

    private void init(View view) {
        myRef = FirebaseDatabase.getInstance().getReference("chats").child(ten_lop).child(ten_hs);

        button_send = view.findViewById(R.id.buttonSendPH);
        edit_text_chat = view.findViewById(R.id.editTextChatPH);
        listChat = view.findViewById(R.id.recyclerViewList_chatPH);
        GiaoVienDAO giaoVienDAO = new GiaoVienDAO();
        giaoVienDAO.getEmailGvcn(ten_gvcn);
    }

    private void setUpViewChat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listChat.setLayoutManager(linearLayoutManager);
        Query queryChat = myRef;
        FirebaseRecyclerAdapter<ChatItem, ChatViewHolder> adapter = new
                FirebaseRecyclerAdapter<ChatItem, ChatViewHolder>(ChatItem.class,
                        R.layout.form_chat_never_used, ChatViewHolder.class, queryChat) {
                    @Override
                    protected void populateViewHolder(ChatViewHolder viewHolder, ChatItem chatItem, int i) {
                        if (sender.equals(chatItem.fromUser) && receiver.equals(chatItem.toUser)) {

                            viewHolder.ReceiverMessage.setVisibility(View.INVISIBLE);

                            viewHolder.SenderMessage.setText(chatItem.chatContent);
                            viewHolder.SenderMessage.setVisibility(View.VISIBLE);

                        } else if (sender.equals(chatItem.toUser) && receiver.equals(chatItem.fromUser)) {

                            viewHolder.ReceiverMessage.setVisibility(View.VISIBLE);
                            viewHolder.ReceiverMessage.setText(chatItem.chatContent);

                            viewHolder.SenderMessage.setVisibility(View.INVISIBLE);
                        }
                        listChat.smoothScrollToPosition(listChat.getAdapter().getItemCount());
                    }
                };
        listChat.setAdapter(adapter);
    }

    private void submitChatContent() {
        String chatContent = edit_text_chat.getText().toString().trim();

        if (TextUtils.isEmpty(chatContent)) {
            edit_text_chat.setError("Vui lòng nhập nội dung");
            return;
        }
        ChatItem chatItem = new ChatItem(sender, chatContent, receiver);
        Map<String, Object> chatValues = chatItem.toMap();

        String key = myRef.push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, chatValues);
        myRef.updateChildren(childUpdates);
    }

}
