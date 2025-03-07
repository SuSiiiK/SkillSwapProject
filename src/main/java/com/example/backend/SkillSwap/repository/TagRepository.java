package com.example.backend.SkillSwap.repository;

import com.example.backend.SkillSwap.payload.request.TagRequest;
import com.example.backend.SkillSwap.payload.response.TagResponse;
import com.example.backend.SkillSwap.service.TagService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TagRepository implements TagService {

    private final JdbcClient jdbcClient;

    public TagRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<TagResponse> getTags() {
        String querySql = """
                SELECT * FROM tag;
                """;

        return jdbcClient.sql(querySql).query(TagResponse.class).list();
    }

    @Override
    public void saveTags(TagRequest tagRequest) {
        String querySql = """
                INSERT INTO tag (id, name) VALUES (?,?);
                """;

        int result = jdbcClient.sql(querySql).params(List.of(
                UUID.randomUUID(),
                tagRequest.name()))
                .update();
    }
}
