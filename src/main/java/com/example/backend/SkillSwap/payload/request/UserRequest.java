package com.example.backend.SkillSwap.payload.request;

public record UserRequest(

        String username,

        String password,

        String email,

        String profession,

        String experience,

        String skills
) {
}
