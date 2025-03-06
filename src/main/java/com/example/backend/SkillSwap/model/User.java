package com.example.backend.SkillSwap.model;

import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private String email;
    private String profession;
    private String experience;

    public User(String username, String email, String profession, String experience, String skills) {
        this.username = username;
        this.email = email;
        this.profession = profession;
        this.experience = experience;
        this.skills = skills;
    }

    public User() {
        this.id = UUID.randomUUID();
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private String skills;


}
