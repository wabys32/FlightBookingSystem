package com.arthurtokarev.flightbookingsystem.mapper;

import com.arthurtokarev.flightbookingsystem.dto.FlightRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.FlightResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.Flight;

public class FlightMapper {

    public static Flight toEntity(FlightRequestDto dto) {

        return Flight.builder()
                .flightNumber(dto.getFlightNumber())
                .departureCity(dto.getDepartureCity())
                .arrivalCity(dto.getArrivalCity())
                .departureTime(dto.getDepartureTime())
                .arrivalTime(dto.getArrivalTime())
                .availableSeats(dto.getAvailableSeats())
                .price(dto.getPrice())
                .build();
    }

    public static FlightResponseDto toDto(Flight flight) {

        return FlightResponseDto.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .departureCity(flight.getDepartureCity())
                .arrivalCity(flight.getArrivalCity())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .availableSeats(flight.getAvailableSeats())
                .price(flight.getPrice())
                .build();
    }
}