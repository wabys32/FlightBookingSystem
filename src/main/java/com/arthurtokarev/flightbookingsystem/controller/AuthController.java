package com.arthurtokarev.flightbookingsystem.controller;

import com.arthurtokarev.flightbookingsystem.dto.AuthResponseDto;
import com.arthurtokarev.flightbookingsystem.dto.LoginRequestDto;
import com.arthurtokarev.flightbookingsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDto login(
            @RequestBody LoginRequestDto dto
    ) {

        return authService.login(dto);
    }
}