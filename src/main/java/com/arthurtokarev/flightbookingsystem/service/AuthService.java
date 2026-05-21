package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.AuthResponseDto;
import com.arthurtokarev.flightbookingsystem.dto.LoginRequestDto;
import com.arthurtokarev.flightbookingsystem.entity.User;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import com.arthurtokarev.flightbookingsystem.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
                        new BadCredentialsException(
                                "Invalid username or password"
                        )
                );

        boolean matches =
                passwordEncoder.matches(
                        dto.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            log.warn("Failed login attempt for username {}", dto.getUsername());

            throw new BadCredentialsException(
                    "Invalid username or password"
            );
        }

        String token =
                jwtUtil.generateToken(
                        user.getUsername(),
                        user.getRole()
                );

        log.info("User {} logged in successfully", user.getUsername());

        return new AuthResponseDto(token);
    }
}
