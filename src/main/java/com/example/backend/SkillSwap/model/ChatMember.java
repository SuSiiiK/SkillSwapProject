package com.example.backend.SkillSwap.model;

import java.util.UUID;

public class ChatMember {

    private UUID id;

    private UUID chatId;

    private UUID userId;

    public ChatMember() {
        this.id = UUID.randomUUID();
    }

    public ChatMember(UUID chatId, UUID userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
