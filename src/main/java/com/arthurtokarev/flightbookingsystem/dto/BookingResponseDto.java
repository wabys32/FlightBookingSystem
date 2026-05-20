package com.arthurtokarev.flightbookingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponseDto {

    private Long bookingId;

    private String username;

    private String flightNumber;

    private LocalDateTime bookingTime;
}