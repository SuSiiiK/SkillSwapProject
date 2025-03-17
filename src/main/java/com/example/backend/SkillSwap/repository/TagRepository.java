package com.example.backend.SkillSwap.repository;

import com.example.backend.SkillSwap.model.PostTag;
import com.example.backend.SkillSwap.model.Tag;
import com.example.backend.SkillSwap.payload.request.TagRequest;
import com.example.backend.SkillSwap.payload.response.PostResponse;
import com.example.backend.SkillSwap.payload.response.TagResponse;
import com.example.backend.SkillSwap.service.TagService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public void saveTags(Tag tagRequest) {
        String querySql = """
                INSERT INTO tag (id, name) VALUES (?,?);
                """;
        UUID tagId = UUID.randomUUID(); // Генерируем новый UUID
        tagRequest.setId(tagId);

        int result = jdbcClient.sql(querySql).params(List.of(
                        tagId,
                        tagRequest.getName()))
                .update();
    }

    public void saveTagsModel(Tag tag) {
        String querySql = """
                INSERT INTO tag (id, name) VALUES (?,?);
                """;

        int result = jdbcClient.sql(querySql).params(List.of(
                        UUID.randomUUID(),
                        tag.getName()))
                .update();
    }

    @Override
    public Tag findTagByName(String name) {
        String querySql = """
                SELECT * FROM tag WHERE name = :name;
                """;
        List<Tag> tags = jdbcClient.sql(querySql).param("name", name).query(Tag.class).list();
        if (tags.isEmpty()) {
            return null;
        }
        return tags.get(0);
    }

    @Override
    public List<TagResponse> getTagsByPostId(UUID postId) {
        String querySql = """
                SELECT tag_id FROM post_tag WHERE post_id = :postId;
                """;

        List<UUID> tagIds = jdbcClient.sql(querySql).param("postId", postId).query(UUID.class).list();

        if (tagIds.isEmpty()) {
            return Collections.emptyList();
        }
        String tagIdsString = tagIds.stream()
                .map(UUID::toString)
                .collect(Collectors.joining("','", "'", "'"));

        String tagQuery = " SELECT id, name FROM tag WHERE id IN (" + tagIdsString + ");";

        List<TagResponse> tagList = jdbcClient.sql(tagQuery).
                param("tagId", tagIds).
                query((rs, rowNum) -> new TagResponse(
                        String.valueOf(rs.getObject("id", UUID.class)),
                        rs.getString("name")
                )).list();

        return tagList;
    }

}
