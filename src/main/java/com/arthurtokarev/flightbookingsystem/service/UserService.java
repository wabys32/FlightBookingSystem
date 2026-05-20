package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.UserRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.UserResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.User;
import com.arthurtokarev.flightbookingsystem.mapper.UserMapper;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto dto) {

        User user = UserMapper.toEntity(dto);

        User savedUser = userRepository.save(user);

        return UserMapper.toDto(savedUser);
    }
}