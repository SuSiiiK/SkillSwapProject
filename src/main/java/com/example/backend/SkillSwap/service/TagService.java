package com.example.backend.SkillSwap.service;

import com.example.backend.SkillSwap.payload.request.TagRequest;
import com.example.backend.SkillSwap.payload.response.TagResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    List<TagResponse> getTags();

    void saveTags(TagRequest tagRequest);
}
