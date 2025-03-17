package com.example.backend.SkillSwap.repository;

import com.example.backend.SkillSwap.model.User;
import com.example.backend.SkillSwap.payload.request.UserRequest;
import com.example.backend.SkillSwap.payload.response.UserResponse;
import com.example.backend.SkillSwap.service.UserService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository implements UserService {

    private final JdbcClient jdbcClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepository(JdbcClient jdbcClient, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jdbcClient = jdbcClient;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveUserData(UserRequest userRequest) {
        String querySql = """
                INSERT INTO users (
                    id,
                    username,
                    password,
                    email,
                    profession,
                    experience,
                    skills,
                    enabled
                ) VALUES (?,?,?,?,?,?,?,?);
                """;
        try {
            String encryptedPassword = bCryptPasswordEncoder.encode(userRequest.password());

            int result = jdbcClient.sql(querySql).params(
                    UUID.randomUUID(),
                    userRequest.username(),
                    encryptedPassword,
                    userRequest.email(),
                    userRequest.experience(),
                    userRequest.profession(),
                    userRequest.skills(),
                    true
            ).update();

            if (result == 0) {
                throw new RuntimeException("Failed to insert user.");
            }

            addAuthorities(userRequest.username());
        } catch (Exception e) {
            System.err.println("Error in saving user data: " + e.getMessage());
            e.printStackTrace();  // Печатаем полную трассировку ошибки
        }
    }

    public void addAuthorities(String username) {
        String querySql = """
            INSERT INTO authorities (username, authority) VALUES (?, ?);
            """;

        try {
            int result = jdbcClient.sql(querySql).params(username, "ROLE_USER").update();
            if (result == 0) {
                throw new RuntimeException("Failed to insert authority.");
            }
        } catch (Exception e) {
            System.err.println("Error in adding authorities: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<UserResponse> getUsers() {
        String querySql = """
                SELECT * FROM users;
                """;

        return jdbcClient.sql(querySql).query(UserResponse.class).list();
    }

    @Override
    public UserResponse getUserByUserName(String username) {
        String querySql = """
            SELECT * FROM users WHERE username = :username;
            """;
        return jdbcClient.sql(querySql)
                .param("username", username)
                .query(UserResponse.class)
                .single();
    }

    public UserResponse getUserById(UUID id) {
        String querySql = """
            SELECT * FROM users WHERE id = :id;
            """;
        return jdbcClient.sql(querySql)
                .param("id", id)
                .query(UserResponse.class)
                .single();
    }
    @Override
    public User getUserByUserNameModel(String username) {
        String querySql = """
            SELECT * FROM users WHERE username = :username;
            """;
        return jdbcClient.sql(querySql)
                .param("username", username)
                .query(User.class)
                .single();
    }

    @Override
    public void deleteUserById(UUID idUser) {
        String query = """
                DELETE FROM users WHERE id = :idUser;
                """;

        int updated = jdbcClient.sql(query)
                .param("idUser", idUser)
                .update();
    }

    @Override
    public void updateUserById(UUID idUser, UserRequest userRequest) {
        // Создаем динамический SQL-запрос
        StringBuilder queryBuilder = new StringBuilder("UPDATE users SET ");
        List<Object> params = new ArrayList<>();

        if (userRequest.username() != null) {
            queryBuilder.append("username = ?, ");
            params.add(userRequest.username());
        }
        if (userRequest.email() != null) {
            queryBuilder.append("email = ?, ");
            params.add(userRequest.email());
        }
        if (userRequest.profession() != null) {
            queryBuilder.append("profession = ?, ");
            params.add(userRequest.profession());
        }
        if (userRequest.experience() != null) {
            queryBuilder.append("experience = ?, ");
            params.add(userRequest.experience());
        }
        if (userRequest.skills() != null) {
            queryBuilder.append("skills = ?, ");
            params.add(userRequest.skills());
        }

        if (!params.isEmpty()) {
            queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        }

        // Добавляем условие WHERE
        queryBuilder.append(" WHERE id = ?");
        params.add(idUser);

        // Выполняем запрос
        int result = jdbcClient.sql(queryBuilder.toString())
                .params(params)
                .update();
    }
}
