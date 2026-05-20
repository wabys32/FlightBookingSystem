package com.arthurtokarev.flightbookingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightResponseDto {

    private Long id;

    private String flightNumber;

    private String departureCity;

    private String arrivalCity;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Integer availableSeats;

    private Double price;
}