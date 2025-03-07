package com.example.backend.SkillSwap.payload.response;

import java.util.UUID;

public record UserResponse (
        UUID id,

        String username,

        String email,

        String profession,

        String experience,

        String skills
){
}
