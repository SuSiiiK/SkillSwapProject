package com.example.backend.SkillSwap.service;

import com.example.backend.SkillSwap.model.User;
import com.example.backend.SkillSwap.payload.request.UserRequest;
import com.example.backend.SkillSwap.payload.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    void saveUserData(UserRequest userRequest);

    List<UserResponse> getUsers();

    void deleteUserById(UUID idUser);

    void updateUserById(UUID id, UserRequest userRequest);
}
