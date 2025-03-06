package com.example.backend.SkillSwap.model;

import java.util.UUID;

public class PostTag {

    private UUID id;

    private UUID postId;

    private UUID tagId;

    public PostTag() {
        this.id = UUID.randomUUID();
    }

    public PostTag(UUID postId, UUID tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getTagId() {
        return tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }
}
