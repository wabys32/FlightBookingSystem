package com.arthurtokarev.flightbookingsystem.controller;

import com.arthurtokarev.flightbookingsystem.dto.FileResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.FileAttachment;
import com.arthurtokarev.flightbookingsystem.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public FileResponseDto uploadFile(
            @RequestParam("file") MultipartFile file
    ) {

        return fileStorageService.storeFile(file);
    }

    @GetMapping
    public List<FileResponseDto> getAllFiles() {

        return fileStorageService.getAllFiles();
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long id
    ) {

        FileAttachment metadata =
                fileStorageService.getFileMetadata(id);

        Resource resource =
                fileStorageService.loadFileAsResource(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        metadata.getContentType() == null
                                ? MediaType.APPLICATION_OCTET_STREAM_VALUE
                                : metadata.getContentType()
                ))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                metadata.getOriginalFileName() + "\""
                )
                .body(resource);
    }
}
