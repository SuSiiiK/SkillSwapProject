package com.example.backend.SkillSwap.repository;

import com.example.backend.SkillSwap.model.User;
import com.example.backend.SkillSwap.payload.request.UserRequest;
import com.example.backend.SkillSwap.payload.response.UserResponse;
import com.example.backend.SkillSwap.service.UserService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository implements UserService {

    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void saveUserData(UserRequest userRequest) {
        String querySql = """
                INSERT INTO "user" (
                    id,
                    username,
                    email,
                    profession,
                    experience,
                    skills
                ) VALUES (?,?,?,?,?,?);
                """;

        int result = jdbcClient.sql(querySql).params(
                UUID.randomUUID(),
                userRequest.username(),
                userRequest.email(),
                userRequest.experience(),
                userRequest.profession(),
                userRequest.skills()
        ).update();
    }

    @Override
    public List<UserResponse> getUsers() {
        String querySql = """
                SELECT * FROM "user";
                """;

        return jdbcClient.sql(querySql).query(UserResponse.class).list();
    }

    @Override
    public void deleteUserById(UUID idUser) {
        String query = """
                DELETE FROM "user" WHERE id = :idUser;
                """;

        int updated = jdbcClient.sql(query)
                .param("idUser", idUser)
                .update();
    }

    @Override
    public void updateUserById(UUID idUser, UserRequest userRequest) {
        String querySql = """
                UPDATE "user" SET
                   username = ?,
                    email = ?,
                    profession = ?,
                    experience = ?,
                    skills = ? WHERE id = ?
                """;
        int result = jdbcClient.sql(querySql).params(List.of(
                userRequest.username(),
                userRequest.email(),
                userRequest.experience(),
                userRequest.profession(),
                userRequest.skills(),
                idUser
        )).update();
    }
}
