package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.BookingRequestDto;
import com.arthurtokarev.flightbookingsystem.dto.BookingResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.Booking;
import com.arthurtokarev.flightbookingsystem.entity.Flight;
import com.arthurtokarev.flightbookingsystem.entity.User;
import com.arthurtokarev.flightbookingsystem.exception.NoAvailableSeatsException;
import com.arthurtokarev.flightbookingsystem.exception.ResourceNotFoundException;
import com.arthurtokarev.flightbookingsystem.repository.BookingRepository;
import com.arthurtokarev.flightbookingsystem.repository.FlightRepository;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;

    public BookingResponseDto bookFlight(
            BookingRequestDto dto
    ) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Flight not found"));

        if (flight.getAvailableSeats() <= 0) {
            throw new NoAvailableSeatsException(
                    "No available seats for this flight"
            );
        }

        flight.setAvailableSeats(
                flight.getAvailableSeats() - 1
        );

        Booking booking = Booking.builder()
                .bookingTime(LocalDateTime.now())
                .user(user)
                .flight(flight)
                .build();

        bookingRepository.save(booking);

        flightRepository.save(flight);

        return BookingResponseDto.builder()
                .bookingId(booking.getId())
                .username(user.getUsername())
                .flightNumber(flight.getFlightNumber())
                .bookingTime(booking.getBookingTime())
                .build();
    }
}