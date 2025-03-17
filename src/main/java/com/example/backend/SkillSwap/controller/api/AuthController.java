package com.example.backend.SkillSwap.controller.api;

import com.example.backend.SkillSwap.payload.request.UserCreditalsRecord;
import com.example.backend.SkillSwap.payload.response.JwtResponse;
import com.example.backend.SkillSwap.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authMapping(@RequestBody UserCreditalsRecord userCreditalsRecord) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userCreditalsRecord.username(), userCreditalsRecord.password());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            logger.info("Получены данные для аутентификации: username = {}", userCreditalsRecord.username());

            String token = jwtUtil.getSecretToken(userCreditalsRecord.username());

            logger.info("Токен успешно сгенерирован для пользователя {}: {}", userCreditalsRecord.username(), token);

            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Ошибка при аутентификации пользователя: {}", userCreditalsRecord.username(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

