package com.arthurtokarev.flightbookingsystem.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(
                jwtUtil,
                "secret",
                "yourVerySecretKeyYourVerySecretKey123456"
        );
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L);
    }

    @Test
    void generatedTokenContainsUsernameAndValidatesForSameUser() {

        String token = jwtUtil.generateToken("arthur2", "USER");
        UserDetails userDetails =
                new User("arthur2", "password", List.of());

        assertThat(jwtUtil.extractUsername(token))
                .isEqualTo("arthur2");
        assertThat(jwtUtil.validateToken(token, userDetails))
                .isTrue();
    }

    @Test
    void tokenDoesNotValidateForDifferentUser() {

        String token = jwtUtil.generateToken("arthur2", "USER");
        UserDetails userDetails =
                new User("another-user", "password", List.of());

        assertThat(jwtUtil.validateToken(token, userDetails))
                .isFalse();
    }
}
