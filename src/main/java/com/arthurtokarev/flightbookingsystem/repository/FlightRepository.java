package com.arthurtokarev.flightbookingsystem.repository;

import com.arthurtokarev.flightbookingsystem.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Page<Flight> findByDepartureCityContainingIgnoreCase(
            String departureCity,
            Pageable pageable
    );

    Page<Flight> findByArrivalCityContainingIgnoreCase(
            String arrivalCity,
            Pageable pageable
    );
}