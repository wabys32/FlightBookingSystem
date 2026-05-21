package com.arthurtokarev.flightbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlightBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightBookingSystemApplication.class, args);
    }

}
