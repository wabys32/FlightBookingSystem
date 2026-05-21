package com.arthurtokarev.flightbookingsystem.controller;

import com.arthurtokarev.flightbookingsystem.service.AsyncReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class AsyncReportController {

    private final AsyncReportService asyncReportService;

    @GetMapping("/flights")
    public CompletableFuture<Map<String, Object>> getFlightReport() {

        return asyncReportService.generateFlightReport();
    }

    @GetMapping("/bookings")
    public CompletableFuture<Map<String, Object>> getBookingReport() {

        return asyncReportService.generateBookingReport();
    }

    @GetMapping("/system")
    public CompletableFuture<Map<String, Object>> getSystemReport() {

        return asyncReportService.generateSystemReport();
    }
}
