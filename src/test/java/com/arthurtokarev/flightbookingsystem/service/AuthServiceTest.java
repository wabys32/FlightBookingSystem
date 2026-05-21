package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.AuthResponseDto;
import com.arthurtokarev.flightbookingsystem.dto.LoginRequestDto;
import com.arthurtokarev.flightbookingsystem.entity.User;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import com.arthurtokarev.flightbookingsystem.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private final UserRepository userRepository =
            mock(UserRepository.class);

    private final PasswordEncoder passwordEncoder =
            mock(PasswordEncoder.class);

    private final JwtUtil jwtUtil =
            mock(JwtUtil.class);

    private final AuthService authService =
            new AuthService(userRepository, passwordEncoder, jwtUtil);

    @Test
    void loginReturnsJwtTokenForValidCredentials() {

        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("arthur2");
        dto.setPassword("123456");

        User user = User.builder()
                .username("arthur2")
                .password("encoded-password")
                .role("USER")
                .build();

        when(userRepository.findByUsername("arthur2"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encoded-password"))
                .thenReturn(true);
        when(jwtUtil.generateToken("arthur2", "USER"))
                .thenReturn("jwt-token");

        AuthResponseDto response = authService.login(dto);

        assertThat(response.getToken()).isEqualTo("jwt-token");
    }

    @Test
    void loginRejectsInvalidPassword() {

        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("arthur2");
        dto.setPassword("wrong-password");

        User user = User.builder()
                .username("arthur2")
                .password("encoded-password")
                .role("USER")
                .build();

        when(userRepository.findByUsername("arthur2"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "encoded-password"))
                .thenReturn(false);

        assertThatThrownBy(() -> authService.login(dto))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid username or password");

        verify(jwtUtil, never()).generateToken(any(), any());
    }
}
