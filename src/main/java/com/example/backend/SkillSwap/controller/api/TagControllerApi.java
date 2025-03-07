package com.example.backend.SkillSwap.controller.api;


import com.example.backend.SkillSwap.model.Tag;
import com.example.backend.SkillSwap.payload.request.TagRequest;
import com.example.backend.SkillSwap.payload.response.TagResponse;
import com.example.backend.SkillSwap.service.TagService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagControllerApi {

    private final TagService tagService;

    public TagControllerApi(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/get-tags")
    public ResponseEntity<List<TagResponse>> getListTags() {
        List<TagResponse> listTags = tagService.getTags();
        return new ResponseEntity<>(listTags, HttpStatus.OK);
    }


    @PostMapping("/save-tag")
    public ResponseEntity<String> saveTags(@RequestBody TagRequest tagRequest) {
        try {
            tagService.saveTags(tagRequest);
            return new ResponseEntity<>("Tag was created", HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>("Tag was not created", HttpStatus.BAD_REQUEST);
        }
    }
}
