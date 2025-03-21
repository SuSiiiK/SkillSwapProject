package com.example.backend.SkillSwap.payload.response;

import java.util.UUID;

public record ChatResponse(
        UUID id,
        UUID chatId,
        UUID userId
) {
}
