package com.example.backend.SkillSwap.payload.request;

public record UserRequest(

        String username,

        String email,

        String profession,

        String experience,

        String skills
) {
}
