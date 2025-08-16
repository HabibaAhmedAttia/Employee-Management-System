package employee.backend.controller;

import employee.backend.dto.UserLoginRequest;
import employee.backend.dto.UserRegisterRequest;
import employee.backend.exception.ApiResponse;
import employee.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody UserRegisterRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.ok(ApiResponse.success("User registered successfully", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody UserLoginRequest request) {
        try {
            String token = userService.login(request);
            Map<String, String> data = new HashMap<>();
            data.put("token", token);

            return ResponseEntity.ok(ApiResponse.success("Login successful", data));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }
}