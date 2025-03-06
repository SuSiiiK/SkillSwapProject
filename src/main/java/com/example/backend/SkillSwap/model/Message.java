package com.example.backend.SkillSwap.model;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.UUID;

public class Message {

    private UUID id;

    private String MessageText;

    private MultipartFile image;

    private UUID chatId;

    private UUID userId;

    private Timestamp created_at;

    public Message() {
        this.id = UUID.randomUUID();
    }

    public Message(String messageText, MultipartFile image, UUID chatId, UUID userId, Timestamp created_at) {
        MessageText = messageText;
        this.image = image;
        this.chatId = chatId;
        this.userId = userId;
        this.created_at = created_at;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
