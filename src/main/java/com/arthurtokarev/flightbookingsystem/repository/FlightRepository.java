package com.arthurtokarev.flightbookingsystem.repository;

import com.arthurtokarev.flightbookingsystem.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightRepository
        extends JpaRepository<Flight, Long> {

    Page<Flight> findAll(Pageable pageable);

    Page<Flight> findByDepartureCityContainingIgnoreCase(
            String departureCity,
            Pageable pageable
    );

    Page<Flight> findByPriceBetween(
            Double minPrice,
            Double maxPrice,
            Pageable pageable
    );
}