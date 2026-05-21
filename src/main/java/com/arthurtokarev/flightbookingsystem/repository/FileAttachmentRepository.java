package com.arthurtokarev.flightbookingsystem.repository;

import com.arthurtokarev.flightbookingsystem.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileAttachmentRepository
        extends JpaRepository<FileAttachment, Long> {
}
