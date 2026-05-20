package com.arthurtokarev.flightbookingsystem.repository;

import com.arthurtokarev.flightbookingsystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {
}