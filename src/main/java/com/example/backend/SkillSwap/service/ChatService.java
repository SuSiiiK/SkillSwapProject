package com.example.backend.SkillSwap.service;


import com.example.backend.SkillSwap.payload.request.MessageRequest;
import com.example.backend.SkillSwap.payload.response.ChatResponse;
import com.example.backend.SkillSwap.payload.response.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ChatService {

    boolean isChatExist(UUID userIdFirst, UUID userIdSecond);

    UUID createChat();

    void connectionUserChat(UUID chatId, UUID userId);

    void createChatForUsers(UUID userIdFirst, UUID userIdSecond);

    void sendMessage(MessageRequest messageRequest);

    List<MessageResponse> getAllMessage(UUID chatId);

    List<ChatResponse> getChatsByUserId(UUID userId);

    List<ChatResponse> getUserIdByChat(UUID chatId);

    byte[] getImageByChatId(UUID chatId);
}
