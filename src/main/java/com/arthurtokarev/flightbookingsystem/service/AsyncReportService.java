package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.repository.BookingRepository;
import com.arthurtokarev.flightbookingsystem.repository.FileAttachmentRepository;
import com.arthurtokarev.flightbookingsystem.repository.FlightRepository;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncReportService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FileAttachmentRepository fileAttachmentRepository;

    @Async("applicationTaskExecutor")
    public CompletableFuture<Map<String, Object>> generateFlightReport() {

        Map<String, Object> report = Map.of(
                "type", "flights",
                "totalFlights", flightRepository.count(),
                "generatedAt", LocalDateTime.now()
        );

        return CompletableFuture.completedFuture(report);
    }

    @Async("applicationTaskExecutor")
    public CompletableFuture<Map<String, Object>> generateBookingReport() {

        Map<String, Object> report = Map.of(
                "type", "bookings",
                "totalBookings", bookingRepository.count(),
                "generatedAt", LocalDateTime.now()
        );

        return CompletableFuture.completedFuture(report);
    }

    @Async("applicationTaskExecutor")
    public CompletableFuture<Map<String, Object>> generateSystemReport() {

        Map<String, Object> report = Map.of(
                "type", "system",
                "totalUsers", userRepository.count(),
                "totalFiles", fileAttachmentRepository.count(),
                "generatedAt", LocalDateTime.now()
        );

        return CompletableFuture.completedFuture(report);
    }
}
