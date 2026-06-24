package backend.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.auth.dto.LoginResponse;
import backend.auth.dto.RescueUserDTO;
import backend.auth.entity.User;
import backend.auth.jwt.JwtUtil;
import backend.auth.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // REGISTER USER
    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {

            throw new RuntimeException(
                    "User already exists with this email");
        }

        return userRepository.save(user);
    }

    // LOGIN USER
    public LoginResponse loginUser(
            String email,
            String password) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        if (!user.getPassword().equals(password)) {

            throw new RuntimeException(
                    "Invalid password");
        }

        String token =
                JwtUtil.generateToken(
                        user.getEmail());

        return new LoginResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }

    public User getUserFromToken(
            String token) {

        String email =
                JwtUtil.extractEmail(token);

        return userRepository
                .findByEmail(email)
                .orElseThrow();
    }

    public List<RescueUserDTO>
getRescueUsers() {

        return userRepository
                .findByRole("RESCUE")
                .stream()
                .map(user ->
                        new RescueUserDTO(
                                user.getId(),
                                user.getName()))
                .collect(Collectors.toList());
        }
}