package com.aviatickets.storage.repository;

import com.aviatickets.storage.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
}
