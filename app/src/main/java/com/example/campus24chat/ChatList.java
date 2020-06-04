package com.example.campus24chat;

public class ChatList {
    String chat_icon;
    String chat_id;
    String chats_name;
    String last_message;
    String chat_type;

    public ChatList() {
    }

    public ChatList(String chat_icon, String chat_id, String chats_name, String last_message, String chat_type) {
        this.chat_icon = chat_icon;
        this.chat_id = chat_id;
        this.chats_name = chats_name;
        this.last_message = last_message;
        this.chat_type = chat_type;
    }

    public String getChat_icon() {
        return chat_icon;
    }

    public void setChat_icon(String chat_icon) {
        this.chat_icon = chat_icon;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getChats_name() {
        return chats_name;
    }

    public void setChats_name(String chats_name) {
        this.chats_name = chats_name;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getChat_type() {
        return chat_type;
    }

    public void setChat_type(String chat_type) {
        this.chat_type = chat_type;
    }
}
