package com.example.backend.SkillSwap.model;


import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.UUID;

public class Post {

    private UUID id;

    private UUID userId;

    private UUID tagsId;

    private String description;

    private MultipartFile image;

    private Timestamp created_at;

    public Post() {
        this.id = UUID.randomUUID();
    }

    public Post(UUID userId, UUID tagsId, String description, MultipartFile image, Timestamp created_at) {
        this.userId = userId;
        this.tagsId = tagsId;
        this.description = description;
        this.image = image;
        this.created_at = created_at;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getTagsId() {
        return tagsId;
    }

    public void setTagsId(UUID tagsId) {
        this.tagsId = tagsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
