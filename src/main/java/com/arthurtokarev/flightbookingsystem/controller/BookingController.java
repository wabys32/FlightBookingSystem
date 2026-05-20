package com.arthurtokarev.flightbookingsystem.controller;

import com.arthurtokarev.flightbookingsystem.dto.BookingRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.BookingResponseDto;
import com.arthurtokarev.flightbookingsystem.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponseDto bookFlight(
            @Valid @RequestBody BookingRequestDto dto
    ) {

        return bookingService.bookFlight(dto);
    }
}