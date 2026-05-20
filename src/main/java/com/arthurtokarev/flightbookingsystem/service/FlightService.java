package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.FlightRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.FlightResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.Flight;
import com.arthurtokarev.flightbookingsystem.mapper.FlightMapper;
import com.arthurtokarev.flightbookingsystem.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightResponseDto createFlight(
            FlightRequestDto dto
    ) {

        Flight flight = FlightMapper.toEntity(dto);

        Flight savedFlight = flightRepository.save(flight);

        return FlightMapper.toDto(savedFlight);
    }

    public Page<FlightResponseDto> getAllFlights(
            int page,
            int size,
            String sortBy
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy)
        );

        Page<Flight> flights =
                flightRepository.findAll(pageable);

        return flights.map(FlightMapper::toDto);
    }


    public Page<FlightResponseDto> searchFlights(
            String departureCity,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Flight> flights =
                flightRepository
                        .findByDepartureCityContainingIgnoreCase(
                                departureCity,
                                pageable
                        );

        return flights.map(FlightMapper::toDto);
    }

    public Page<FlightResponseDto> filterFlightsByPrice(
            Double minPrice,
            Double maxPrice,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Flight> flights =
                flightRepository.findByPriceBetween(
                        minPrice,
                        maxPrice,
                        pageable
                );

        return flights.map(FlightMapper::toDto);
    }
}