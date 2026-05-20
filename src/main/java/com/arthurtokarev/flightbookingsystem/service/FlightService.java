package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.FlightRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.FlightResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.Flight;
import com.arthurtokarev.flightbookingsystem.mapper.FlightMapper;
import com.arthurtokarev.flightbookingsystem.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}