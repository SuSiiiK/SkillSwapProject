package com.example.backend.SkillSwap.repository;

import com.example.backend.SkillSwap.model.Tag;
import com.example.backend.SkillSwap.model.User;
import com.example.backend.SkillSwap.payload.request.PostRequest;
import com.example.backend.SkillSwap.payload.response.PostResponse;
import com.example.backend.SkillSwap.service.PostService;
import com.example.backend.SkillSwap.service.TagService;
import com.example.backend.SkillSwap.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class PostRepository implements PostService {

    private final JdbcClient jdbcClient;
    private final TagService tagService;

    public PostRepository(JdbcClient jdbcClient, TagService tagService) {
        this.jdbcClient = jdbcClient;
        this.tagService = tagService;
    }

    @Override
    public void savePost(PostRequest postRequest) {
        String querySql = """
                 INSERT INTO post (
                       id,
                       user_id,
                       image,
                       description,
                       created_at
                ) VALUES (?,?,?,?,?);
                """;
        UUID postId = UUID.randomUUID();

        Timestamp createdAt = postRequest.created_at() != null ? postRequest.created_at() : Timestamp.valueOf(LocalDateTime.now());

        try {
            int result = jdbcClient.sql(querySql)
                    .params(
                            postId,
                            postRequest.userId(),
                            postRequest.image().getBytes(),
                            postRequest.description(),
                            createdAt
                    ).update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (postRequest.tagsName() != null && !postRequest.tagsName().isEmpty()) {

            for (String name : postRequest.tagsName()) {
                Tag tag = tagService.findTagByName(name);

                if (tag == null) {
                    tag = new Tag();
                    tag.setName(name);
                    tagService.saveTags(tag);
                }

            String insertPostTagSql = """
                    INSERT INTO post_tag(
                    id,
                    post_id,
                    tag_id)
                    VALUES (?, ?, ?);
                    """;
            jdbcClient.sql(insertPostTagSql).params(UUID.randomUUID(), postId, tag.getId()).update();

            }
        }else {
            System.out.println("No");
        }
    }


    @Override
    public List<PostResponse> getListPost() {
        String querySql = """
                SELECT * FROM post;
                """;

        return jdbcClient.sql(querySql).query(PostResponse.class).list();
    }

    @Override
    public List<PostResponse> findPostByUserId(UUID userId) {
        String querySql = """
                SELECT * FROM post WHERE user_id = :user_id
                """;

        return jdbcClient.sql(querySql).param("user_id", userId).query(PostResponse.class).list();
    }

    @Override
    public byte[] getImageByPostId(UUID postId) {
        String querySql = """
                SELECT image FROM post WHERE id = :postId;
                """;
        return jdbcClient.sql(querySql)
                .param("postId", postId)
                .query(byte[].class)
                .optional().orElse(null);
    }

    @Override
    public MediaType getImageMediaType(byte[] imageBytes) {
        if (imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8) {
            return MediaType.IMAGE_JPEG;
        } else if (imageBytes[0] == (byte) 0x89 && imageBytes[1] == (byte) 0x50) {
            return MediaType.IMAGE_PNG;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
