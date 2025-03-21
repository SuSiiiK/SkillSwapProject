package com.example.backend.SkillSwap.controller.api;

import com.example.backend.SkillSwap.payload.request.PostRequest;
import com.example.backend.SkillSwap.payload.response.PostResponse;
import com.example.backend.SkillSwap.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PostControllerApi {

    private final PostService postService;

    public PostControllerApi(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/get-posts")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> postResponses = postService.getListPost();
        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/image")
    public ResponseEntity<byte[]> getAllPosts(@PathVariable UUID postId) {
        byte[] imageBytes = postService.getImageByPostId(postId);
        if (imageBytes != null) {
            MediaType mediaType = postService.getImageMediaType(imageBytes);

            return ResponseEntity.ok().contentType(mediaType).body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save-post")
    public ResponseEntity<String> savePost(@ModelAttribute PostRequest postRequest) {
        postService.savePost(postRequest);
        return new ResponseEntity<>("Post was succes saved", HttpStatus.CREATED);
    }

    @GetMapping("/posts/{userId}")
    public ResponseEntity<List<PostResponse>> getPostByUserId(@PathVariable UUID userId) {
        List<PostResponse> list = postService.findPostByUserId(userId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
