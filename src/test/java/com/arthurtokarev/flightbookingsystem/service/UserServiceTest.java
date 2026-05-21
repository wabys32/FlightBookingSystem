package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.UserRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.UserResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.User;
import com.arthurtokarev.flightbookingsystem.exception.DuplicateResourceException;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final UserRepository userRepository =
            mock(UserRepository.class);

    private final PasswordEncoder passwordEncoder =
            mock(PasswordEncoder.class);

    private final UserService userService =
            new UserService(userRepository, passwordEncoder);

    @Test
    void createUserStoresEncodedPasswordAndUserRole() {

        UserRequestDto dto = new UserRequestDto();
        dto.setUsername("arthur2");
        dto.setEmail("arthur2@mail.com");
        dto.setPassword("123456");

        when(passwordEncoder.encode("123456"))
                .thenReturn("encoded-password");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        UserResponseDto response = userService.createUser(dto);

        assertThat(response.getUsername()).isEqualTo("arthur2");
        assertThat(response.getRole()).isEqualTo("USER");
        verify(userRepository).save(argThat(user ->
                user.getPassword().equals("encoded-password")
                        && user.getRole().equals("USER")
        ));
    }

    @Test
    void createUserRejectsDuplicateUsername() {

        UserRequestDto dto = new UserRequestDto();
        dto.setUsername("arthur2");
        dto.setEmail("arthur2@mail.com");
        dto.setPassword("123456");

        when(userRepository.existsByUsername("arthur2"))
                .thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(dto))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Username is already taken");

        verify(userRepository, never()).save(any());
    }
}
