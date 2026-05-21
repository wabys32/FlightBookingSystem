package com.arthurtokarev.flightbookingsystem.service;

import com.arthurtokarev.flightbookingsystem.dto.FileResponseDto;
import com.arthurtokarev.flightbookingsystem.entity.FileAttachment;
import com.arthurtokarev.flightbookingsystem.exception.FileStorageException;
import com.arthurtokarev.flightbookingsystem.exception.ResourceNotFoundException;
import com.arthurtokarev.flightbookingsystem.repository.FileAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileAttachmentRepository fileAttachmentRepository;

    @Value("${file.storage-dir:uploads}")
    private String storageDir;

    public FileResponseDto storeFile(MultipartFile file) {

        if (file.isEmpty()) {

            throw new FileStorageException("File is empty");
        }

        String originalFileName =
                StringUtils.cleanPath(file.getOriginalFilename());

        if (originalFileName.contains("..")) {

            throw new FileStorageException("Invalid file name");
        }

        String storedFileName =
                UUID.randomUUID() + "-" + originalFileName;

        Path storagePath = getStoragePath();
        Path targetPath = storagePath.resolve(storedFileName).normalize();

        try {

            Files.createDirectories(storagePath);
            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException ex) {

            throw new FileStorageException("Could not store file", ex);
        }

        FileAttachment attachment = FileAttachment.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .uploadedAt(LocalDateTime.now())
                .build();

        return toDto(fileAttachmentRepository.save(attachment));
    }

    public List<FileResponseDto> getAllFiles() {

        return fileAttachmentRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public FileAttachment getFileMetadata(Long id) {

        return fileAttachmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("File not found"));
    }

    public Resource loadFileAsResource(Long id) {

        FileAttachment attachment = getFileMetadata(id);
        Path filePath = getStoragePath()
                .resolve(attachment.getStoredFileName())
                .normalize();

        try {

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {

                return resource;
            }

            throw new FileStorageException("File cannot be read");

        } catch (MalformedURLException ex) {

            throw new FileStorageException("File cannot be read", ex);
        }
    }

    private Path getStoragePath() {

        return Paths.get(storageDir)
                .toAbsolutePath()
                .normalize();
    }

    private FileResponseDto toDto(FileAttachment attachment) {

        return FileResponseDto.builder()
                .id(attachment.getId())
                .originalFileName(attachment.getOriginalFileName())
                .contentType(attachment.getContentType())
                .size(attachment.getSize())
                .uploadedAt(attachment.getUploadedAt())
                .downloadUrl("/api/files/" + attachment.getId() + "/download")
                .build();
    }
}
