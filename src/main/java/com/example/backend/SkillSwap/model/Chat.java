package com.example.backend.SkillSwap.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Chat {

    private UUID id;

    private LocalDateTime createdAt;

    public Chat() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
