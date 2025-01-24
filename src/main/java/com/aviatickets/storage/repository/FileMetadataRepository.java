package com.aviatickets.storage.repository;

import com.aviatickets.storage.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {

}
