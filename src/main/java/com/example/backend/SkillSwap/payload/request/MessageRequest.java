package com.example.backend.SkillSwap.payload.request;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.UUID;

public record MessageRequest(

        String messageText,

        MultipartFile image,

        UUID chatId,

        UUID userId,

        Timestamp created_at
) {
}
