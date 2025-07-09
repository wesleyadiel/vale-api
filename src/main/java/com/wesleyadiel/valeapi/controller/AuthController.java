package com.wesleyadiel.valeapi.controller;

import com.wesleyadiel.valeapi.dto.request.LoginRequest;
import com.wesleyadiel.valeapi.dto.request.UserRequest;
import com.wesleyadiel.valeapi.dto.response.ApiResponse;
import com.wesleyadiel.valeapi.dto.response.TokenResponse;
import com.wesleyadiel.valeapi.security.JwtService;
import com.wesleyadiel.valeapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, JwtService jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest login) {
        var auth = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
        authManager.authenticate(auth);
        var token = jwtUtil.generateToken(login.getEmail());

        ApiResponse<TokenResponse> response = new ApiResponse<>(true, "Login realizado com sucesso.", new TokenResponse(token));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/criar")
    public ResponseEntity<ApiResponse<String>> criar(@Valid @RequestBody UserRequest user) {
        userService.createUser(user);

        ApiResponse<String> response = new ApiResponse<>(true, "Usuário criado com sucesso.", null);

        return ResponseEntity.ok(response);
    }
}
