package com.example.backend.SkillSwap.payload.response;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.UUID;

public record PostResponse(

        UUID id,

        UUID userId,

        String description,

        byte[] image,

        Timestamp created_at
) {
}
