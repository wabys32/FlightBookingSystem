package com.arthurtokarev.flightbookingsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FileResponseDto {

    private Long id;

    private String originalFileName;

    private String contentType;

    private Long size;

    private LocalDateTime uploadedAt;

    private String downloadUrl;
}
