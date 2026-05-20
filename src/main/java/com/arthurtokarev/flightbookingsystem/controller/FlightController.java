package com.arthurtokarev.flightbookingsystem.controller;

import com.arthurtokarev.flightbookingsystem.dto.FlightRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.FlightResponseDto;
import com.arthurtokarev.flightbookingsystem.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public FlightResponseDto createFlight(
            @Valid @RequestBody FlightRequestDto dto
    ) {

        return flightService.createFlight(dto);
    }

    @GetMapping
    public Page<FlightResponseDto> getAllFlights(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy
    ) {

        return flightService.getAllFlights(
                page,
                size,
                sortBy
        );
    }

    @GetMapping("/search")
    public Page<FlightResponseDto> searchFlights(

            @RequestParam String departureCity,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size
    ) {

        return flightService.searchFlights(
                departureCity,
                page,
                size
        );
    }


    @GetMapping("/filter")
    public Page<FlightResponseDto> filterFlights(

            @RequestParam Double minPrice,

            @RequestParam Double maxPrice,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size
    ) {

        return flightService.filterFlightsByPrice(
                minPrice,
                maxPrice,
                page,
                size
        );
    }
}