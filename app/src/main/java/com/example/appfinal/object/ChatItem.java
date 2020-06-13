package com.example.appfinal.object;

import java.util.HashMap;
import java.util.Map;

public class ChatItem {
    public String fromUser, chatContent, toUser;

    public ChatItem() {
    }

    public ChatItem(String fromUser, String chatContent, String toUser) {
        this.fromUser = fromUser;
        this.chatContent = chatContent;
        this.toUser = toUser;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fromUser", fromUser);
        result.put("chatContent", chatContent);
        result.put("toUser", toUser);
        return result;
    }
}
