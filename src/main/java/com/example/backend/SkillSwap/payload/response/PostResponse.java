package com.example.backend.SkillSwap.payload.response;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.UUID;

public record PostResponse(

        UUID userId,

        UUID tagsId,

        String description,

        MultipartFile image,

        Timestamp created_at
) {
}
