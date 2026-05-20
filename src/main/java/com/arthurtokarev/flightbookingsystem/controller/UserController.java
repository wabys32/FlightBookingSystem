package com.arthurtokarev.flightbookingsystem.controller;

import com.arthurtokarev.flightbookingsystem.dto.UserRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.UserResponseDto;
import com.arthurtokarev.flightbookingsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto createUser(
            @Valid @RequestBody UserRequestDto dto
    ) {

        return userService.createUser(dto);
    }
}