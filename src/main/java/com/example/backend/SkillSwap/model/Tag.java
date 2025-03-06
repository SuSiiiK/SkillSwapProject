package com.example.backend.SkillSwap.model;

import java.util.UUID;

public class Tag {

    private UUID id;

    private String name;

    public Tag() {
        this.id = UUID.randomUUID();
    }

    public Tag(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
