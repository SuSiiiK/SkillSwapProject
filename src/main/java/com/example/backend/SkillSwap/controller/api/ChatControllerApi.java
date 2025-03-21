package com.example.backend.SkillSwap.controller.api;


import com.example.backend.SkillSwap.payload.request.MessageRequest;
import com.example.backend.SkillSwap.payload.response.ChatResponse;
import com.example.backend.SkillSwap.payload.response.MessageResponse;
import com.example.backend.SkillSwap.service.ChatService;
import com.example.backend.SkillSwap.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ChatControllerApi {

    private final ChatService chatService;
    private final PostService postService;

    public ChatControllerApi(ChatService chatService, PostService postService) {
        this.chatService = chatService;
        this.postService = postService;
    }

    @GetMapping("/getMessage/{chatId}")
    public ResponseEntity<List<MessageResponse>> getAllMessage(@PathVariable UUID chatId) {
        List<MessageResponse> listMessage = chatService.getAllMessage(chatId);
        return new ResponseEntity<>(listMessage, HttpStatus.OK);
    }

    @GetMapping("/message/{messageId}/image")
    public ResponseEntity<byte[]> getImageByChatId(@PathVariable UUID messageId) {
        byte[] imageBytes = chatService.getImageByChatId(messageId);
        if (imageBytes != null) {
            MediaType mediaType = postService.getImageMediaType(imageBytes);

            return ResponseEntity.ok().contentType(mediaType).body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@ModelAttribute MessageRequest messageRequest) {
        chatService.sendMessage(messageRequest);
        return new ResponseEntity<>("Message was send", HttpStatus.CREATED);
    }

    @PostMapping("/create-connection/{userIdFirst}/{userIdSecond}")
    public ResponseEntity<String> createConnectionChatUser(@PathVariable UUID userIdFirst, @PathVariable UUID userIdSecond) {
        boolean isChat = chatService.isChatExist(userIdFirst, userIdSecond);
        if (!isChat) {
            chatService.createChatForUsers(userIdFirst, userIdSecond);
        } else {
            return new ResponseEntity<>("A chat with this user already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Connection was create", HttpStatus.CREATED);
    }

    @GetMapping("/chats/{userId}")
    public ResponseEntity<List<ChatResponse>> getChats(@PathVariable UUID userId) {
        List<ChatResponse> chatIds = chatService.getChatsByUserId(userId);
        return new ResponseEntity<>(chatIds, HttpStatus.OK);
    }

    @GetMapping("/users/{chatId}")
    public ResponseEntity<List<ChatResponse>> getUserIdByChat(@PathVariable UUID chatId) {
        List<ChatResponse> chatIds = chatService.getUserIdByChat(chatId);
        return new ResponseEntity<>(chatIds, HttpStatus.OK);
    }
}
