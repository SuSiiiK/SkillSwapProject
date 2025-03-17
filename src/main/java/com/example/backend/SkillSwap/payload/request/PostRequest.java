package com.example.backend.SkillSwap.payload.request;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public record PostRequest (
        UUID userId,

        List<String> tagsName,

        String description,

        MultipartFile image,

        Timestamp created_at
){
}
