package com.arthurtokarev.flightbookingsystem.mapper;

import com.arthurtokarev.flightbookingsystem.dto.UserRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.UserResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDto dto) {

        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role("USER")
                .build();
    }

    public static UserResponseDto toDto(User user) {

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
