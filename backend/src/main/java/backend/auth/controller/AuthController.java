package backend.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.auth.dto.AuthResponse;
import backend.auth.entity.User;
import backend.auth.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody User user) {
        userService.registerUser(user);
        return new AuthResponse("User registered successfully", null);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody User user) {
        String token = userService.loginUser(user.getEmail(), user.getPassword());
        return new AuthResponse("Login successful", token);
    }
}