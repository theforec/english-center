package com.example.appfinal.fragment.giaoVien;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appfinal.R;
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

public class GV_UserChatFragment extends Fragment implements View.OnClickListener {
    @Nullable
    private Button button_send;
    private EditText edit_text_chat;
    private RecyclerView listChat;
    private String receiver, text_name, ten_lop;
    private String sender;
    private TextView back_to_user, name_receiver, email_receiver;
    private DatabaseReference myRef;
    private GV_UserFragment gv_userFragment;

    void setGv_userFragment(GV_UserFragment gv_userFragment) {
        this.gv_userFragment = gv_userFragment;
    }

    void setSender(String user) {
        this.sender = user;
    }

    void setReceiver(String name, String mail, String lop) {
        this.receiver = mail;
        this.text_name = name;
        this.ten_lop = lop;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_gv_fragment_user_chat, container, false);
        init(view);
        setUpListChat();

        button_send.setOnClickListener(this);
        back_to_user.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        myRef = FirebaseDatabase.getInstance().getReference("chats").child(ten_lop).child(text_name);

        back_to_user = view.findViewById(R.id.text_view_back_to_user_gv);
        name_receiver = view.findViewById(R.id.text_view_name_receiver);
        name_receiver.setText(text_name);
        email_receiver = view.findViewById(R.id.text_view_email_receiver);
        email_receiver.setText(receiver);
        button_send = view.findViewById(R.id.button_send_chat);
        listChat = view.findViewById(R.id.recyclerViewList_chatGV);
        edit_text_chat = view.findViewById(R.id.editTextChatGV);
    }

    private void setUpListChat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listChat.setLayoutManager(linearLayoutManager);
        Query queryChat = myRef;
        FirebaseRecyclerAdapter<ChatItem, ChatViewHolder> adapter =
                new FirebaseRecyclerAdapter<ChatItem, ChatViewHolder>(ChatItem.class,
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_chat:
                submitChatContent();
                edit_text_chat.setText("");
                break;
            case R.id.text_view_back_to_user_gv:
                if (gv_userFragment != null) {
                    gv_userFragment.change(GV_UserFragment.CHANGE_TO_USER_USER);
                }
                break;
            default:
                break;
        }
    }
}
