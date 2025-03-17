package com.example.backend.SkillSwap.service;

import com.example.backend.SkillSwap.model.Tag;
import com.example.backend.SkillSwap.payload.request.TagRequest;
import com.example.backend.SkillSwap.payload.response.PostResponse;
import com.example.backend.SkillSwap.payload.response.TagResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TagService {

    List<TagResponse> getTags();

    void saveTags(Tag tagRequest);

    Tag findTagByName(String name);

    List<TagResponse> getTagsByPostId(UUID postId);
}
