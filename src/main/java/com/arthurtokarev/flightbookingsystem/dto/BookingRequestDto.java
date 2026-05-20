package com.arthurtokarev.flightbookingsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long flightId;
}