package com.arthurtokarev.flightbookingsystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequestDto {

    @NotBlank
    private String flightNumber;

    @NotBlank
    private String departureCity;

    @NotBlank
    private String arrivalCity;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @Min(1)
    private Integer availableSeats;

    @Positive
    private Double price;
}