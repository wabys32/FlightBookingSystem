package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.UserRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.UserResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.User;
import com.arthurtokarev.flightbookingsystem.exception.DuplicateResourceException;
import com.arthurtokarev.flightbookingsystem.mapper.UserMapper;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(UserRequestDto dto) {

        User user = createUniqueUser(dto);
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }

    public UserResponseDto createAdminUser(UserRequestDto dto) {

        User user = createUniqueUser(dto);
        user.setRole("ADMIN");

        User savedUser = userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }

    private User createUniqueUser(UserRequestDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {

            throw new DuplicateResourceException(
                    "Username is already taken"
            );
        }

        if (userRepository.existsByEmail(dto.getEmail())) {

            throw new DuplicateResourceException(
                    "Email is already registered"
            );
        }

        User user = UserMapper.toEntity(dto);

        user.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        return user;
    }
}
