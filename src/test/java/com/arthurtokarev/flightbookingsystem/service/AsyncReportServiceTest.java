package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.repository.BookingRepository;
import com.arthurtokarev.flightbookingsystem.repository.FileAttachmentRepository;
import com.arthurtokarev.flightbookingsystem.repository.FlightRepository;
import com.arthurtokarev.flightbookingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AsyncReportServiceTest {

    private final FlightRepository flightRepository =
            mock(FlightRepository.class);

    private final BookingRepository bookingRepository =
            mock(BookingRepository.class);

    private final UserRepository userRepository =
            mock(UserRepository.class);

    private final FileAttachmentRepository fileAttachmentRepository =
            mock(FileAttachmentRepository.class);

    private final AsyncReportService asyncReportService =
            new AsyncReportService(
                    flightRepository,
                    bookingRepository,
                    userRepository,
                    fileAttachmentRepository
            );

    @Test
    void generateSystemReportIncludesUsersAndFiles() {

        when(userRepository.count()).thenReturn(3L);
        when(fileAttachmentRepository.count()).thenReturn(2L);

        Map<String, Object> report =
                asyncReportService.generateSystemReport().join();

        assertThat(report)
                .containsEntry("type", "system")
                .containsEntry("totalUsers", 3L)
                .containsEntry("totalFiles", 2L);
    }
}
