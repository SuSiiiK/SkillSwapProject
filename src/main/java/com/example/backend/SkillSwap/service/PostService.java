package com.example.backend.SkillSwap.service;

import com.example.backend.SkillSwap.payload.request.PostRequest;
import com.example.backend.SkillSwap.payload.response.PostResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PostService {
    void savePost(PostRequest postRequest);

    List<PostResponse> getListPost();

    List<PostResponse> findPostByUserId(UUID userId);

    byte[] getImageByPostId(UUID postId);

    MediaType getImageMediaType(byte[] imageBytes);
}
