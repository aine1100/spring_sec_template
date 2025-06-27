package com.backend.irire.Service;

import com.backend.irire.Dto.Request.UserAuthRequest;
import com.backend.irire.Model.User;
import com.backend.irire.Repository.RefreshTokenRepository;
import com.backend.irire.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       RefreshTokenRepository refreshTokenRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public User RegisterUser(UserAuthRequest userAuthRequest) {
        if (userRepository.findByEmail(userAuthRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists, please use another email");
        }
        if (userAuthRequest.getFirstName().isEmpty() || userAuthRequest.getLastName().isEmpty() ||
                userAuthRequest.getEmail().isEmpty() || userAuthRequest.getRole() == null) {
            throw new IllegalArgumentException("FirstName, LastName, Email, and Role are required");
        }

        User user = new User();
        user.setEmail(userAuthRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userAuthRequest.getPassword()));
        user.setFirstName(userAuthRequest.getFirstName());
        user.setLastName(userAuthRequest.getLastName());
        user.setRole(userAuthRequest.getRole());

        return userRepository.save(user);
    }

    public User LoginUser(UserAuthRequest userAuthRequest) {
        if (userAuthRequest.getEmail().trim().isEmpty() || userAuthRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Email and Password are required");
        }

        try {
            // Authenticate using Spring Security's AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userAuthRequest.getEmail(),
                            userAuthRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return userRepository.findByEmail(userAuthRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}