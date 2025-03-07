package com.example.backend.SkillSwap.controller.api;


import com.example.backend.SkillSwap.model.User;
import com.example.backend.SkillSwap.payload.request.UserRequest;
import com.example.backend.SkillSwap.payload.response.UserResponse;
import com.example.backend.SkillSwap.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserControllerApi {

    private final UserService userService;

    public UserControllerApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserResponse>> getUsersList() {
        List<UserResponse> listUser = userService.getUsers();
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }

    @PostMapping("/save-user")
    public ResponseEntity<String> saveUser(@RequestBody UserRequest userRequest) {
        try {
            userService.saveUserData(userRequest);
            return new ResponseEntity<>("User was saved", HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<String> deleteUserById(@PathVariable("idUser") UUID idUser) {
        try {
            userService.deleteUserById(idUser);
            return new ResponseEntity<>("User was deleted by id", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Not found user by id", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{idUser}")
    public ResponseEntity<String> updateUserById(@PathVariable("idUser") UUID idUser, @RequestBody UserRequest userRequest) {
        try {
            userService.updateUserById(idUser, userRequest);
            return new ResponseEntity<>("User was update by id", HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Not found user by id", HttpStatus.NOT_FOUND);
        }
    }
}
