package com.wesleyadiel.valeapi.controller;


import com.wesleyadiel.valeapi.dto.response.ApiResponse;
import com.wesleyadiel.valeapi.model.User;
import com.wesleyadiel.valeapi.service.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PermitAll
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable String id) {
        User user = service.getUserById(id);
        ApiResponse<User> response = new ApiResponse<>(true, "Usuário encontrado com sucesso!", user);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        service.deleteUserById(id);
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }
}
