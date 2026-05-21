package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.AuthResponseDto;
import com.arthurtokarev.flightbookingsystem.dto.LoginRequestDto;
import com.arthurtokarev.flightbookingsystem.entity.User;
import com.arthurtokarev.flightbookingsystem.exception.ResourceNotFoundException;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import com.arthurtokarev.flightbookingsystem.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDto login(
            LoginRequestDto dto
    ) {

        User user = userRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        boolean matches =
                passwordEncoder.matches(
                        dto.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            throw new RuntimeException(
                    "Invalid password"
            );
        }

        String token =
                jwtUtil.generateToken(
                        user.getUsername()
                );

        return new AuthResponseDto(token);
    }
}