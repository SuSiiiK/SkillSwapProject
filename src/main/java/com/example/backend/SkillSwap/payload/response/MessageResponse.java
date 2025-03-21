package com.example.backend.SkillSwap.payload.response;


import java.sql.Timestamp;
import java.util.UUID;

public record MessageResponse(
        UUID id,

        String messageText,

        byte[] image,

        UUID chat_id,

        UUID user_id,

        Timestamp created_at
) {
}
