package employee.backend.controller;

import employee.backend.dto.UserLoginRequest;
import employee.backend.dto.UserRegisterRequest;
import employee.backend.exception.ApiResponse;
import employee.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account. Provide a unique username, valid email, and password. Returns a success message if registration is successful."
    )
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody UserRegisterRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.ok(ApiResponse.success("User registered successfully", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        }
    }
    @Operation(
            summary = "Login a user",
            description = "Authenticate a user using email and password. Returns a JWT token in case of successful login. Token must be used for accessing protected endpoints."
    )
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