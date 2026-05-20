package com.arthurtokarev.flightbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;

    private String departureCity;

    private String arrivalCity;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Integer availableSeats;

    private Double price;
}