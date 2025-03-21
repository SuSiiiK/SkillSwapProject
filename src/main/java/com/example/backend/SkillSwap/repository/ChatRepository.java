package com.example.backend.SkillSwap.repository;

import com.example.backend.SkillSwap.payload.request.MessageRequest;
import com.example.backend.SkillSwap.payload.response.ChatResponse;
import com.example.backend.SkillSwap.payload.response.MessageResponse;
import com.example.backend.SkillSwap.service.ChatService;
import com.example.backend.SkillSwap.service.PostService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class ChatRepository implements ChatService {

    private final JdbcClient jdbcClient;
    private final PostService postService;

    public ChatRepository(JdbcClient jdbcClient, PostService postService) {
        this.jdbcClient = jdbcClient;
        this.postService = postService;
    }

    @Override
    public UUID createChat() {
        String queryCreateChat = """
                INSERT INTO chat (id, created_at) VALUES (?, ?);
                """;
        UUID chatID = UUID.randomUUID();
        int result = jdbcClient.sql(queryCreateChat).params(List.of(
                chatID,
                LocalDateTime.now()
        )).update();

        return chatID;
    }

    @Override
    public void connectionUserChat(UUID chatId, UUID userId) {
        String querySql = """
                INSERT INTO chat_members (id, chat_id, user_id) VALUES (?, ?, ?);
                """;

        int result = jdbcClient.sql(querySql).params(List.of(
                UUID.randomUUID(),
                chatId,
                userId
        )).update();
    }

    @Override
    public void createChatForUsers(UUID userIdFirst, UUID userIdSecond) {
        UUID chatId = createChat();

        connectionUserChat(chatId, userIdFirst);
        connectionUserChat(chatId, userIdSecond);
    }

    @Override
    public List<ChatResponse> getChatsByUserId(UUID userId) {
        String sqlQuery = """
                SELECT * FROM chat_members WHERE user_id = :userId
                """;

        return jdbcClient.sql(sqlQuery).param("userId", userId).query(ChatResponse.class).list();
    }

    @Override
    public List<ChatResponse> getUserIdByChat(UUID chatId) {
        String sqlQuery = """
                SELECT * FROM chat_members WHERE chat_id = :chatId
                """;

        return jdbcClient.sql(sqlQuery).param("chatId", chatId).query(ChatResponse.class).list();
    }

    @Override
    public byte[] getImageByChatId(UUID messageId) {
        String querySql = """
                SELECT image FROM message WHERE id = :messageId;
                """;
        return jdbcClient.sql(querySql)
                .param("messageId", messageId)
                .query(byte[].class)
                .optional().orElse(null);
    }

    public boolean isChatExist(UUID userIdFirst, UUID userIdSecond) {
        String query = """
                SELECT COUNT(*)
                FROM chat_members cm1
                JOIN chat_members cm2 ON cm1.chat_id = cm2.chat_id
                WHERE cm1.user_id = :userId1 AND cm2.user_id = :userId2;
                """;

        int count = jdbcClient.sql(query)
                .param("userId1", userIdFirst)
                .param("userId2", userIdSecond)
                .query(Integer.class).single();

        return count > 0;
    }


    public UUID getChatMemberId(UUID chat_id) {
        String sqlQuery = """
                SELECT chat_id FROM chat_members WHERE chat_id = :chat_id;
                """;

        return jdbcClient.sql(sqlQuery).param("chat_id", chat_id).query(UUID.class).single();
    }

    @Override
    public void sendMessage(MessageRequest messageRequest) {
        String querySql = """
                INSERT INTO message (id, message_text, image, chat_id, user_id, created_at)
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        Timestamp createdAt = messageRequest.created_at() != null ? messageRequest.created_at() : Timestamp.valueOf(LocalDateTime.now());
        MultipartFile image = messageRequest.image();
        try {
            byte[] imageBytes = null;
            if (image != null) {
                imageBytes = image.getBytes();
            }

            assert imageBytes != null;

            int result1 = jdbcClient.sql(querySql)
                    .param(1, UUID.randomUUID())
                    .param(2, messageRequest.messageText())
                    .param(3, imageBytes)
                    .param(4, messageRequest.chatId())
                    .param(5, messageRequest.userId())
                    .param(6, createdAt)
                    .update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MessageResponse> getAllMessage(UUID chatId) {
        String sqlQuery = """
                SELECT * FROM message WHERE chat_id = :chatId;
                """;

        return jdbcClient.sql(sqlQuery).param("chatId", chatId).query(MessageResponse.class).list();
    }
}
