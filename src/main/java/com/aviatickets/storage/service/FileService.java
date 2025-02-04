package com.aviatickets.storage.service;

import com.aviatickets.storage.controller.request.UpdateFileRequest;
import com.aviatickets.storage.controller.response.FileResponse;
import com.aviatickets.storage.mapper.FileMapper;
import com.aviatickets.storage.model.FileEntity;
import com.aviatickets.storage.model.FileMetadata;
import com.aviatickets.storage.model.FileStatus;
import com.aviatickets.storage.repository.FileEntityRepository;
import com.aviatickets.storage.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    public static final String FILE_NOT_FOUND = "File not found bu id %s";

    private final FileMetadataRepository fileMetadataRepository;
    private final FileEntityRepository fileEntityRepository;
    private final FileMapper mapper;

    @Transactional
    public FileResponse upload(MultipartFile file) {
        SupportedExtension extension = SupportedExtension.from(file.getOriginalFilename());

        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading file", e);
        }

        FileEntity fileEntity = new FileEntity(bytes);
        fileEntity = fileEntityRepository.save(fileEntity);

        FileMetadata fileMetadata = FileMetadata.builder()
                .file(fileEntity)
                .fileName(file.getOriginalFilename())
                .extension(extension)
                .size(file.getSize())
                .status(FileStatus.ACTIVE)
                .build();

        fileMetadata = fileMetadataRepository.save(fileMetadata);

        return mapper.toFileResponse(fileMetadata);
    }

    @Transactional(readOnly = true)
    public FileResponse getMetadataById(UUID id) {
        return mapper.toFileResponse(findById(id));
    }

    public FileMetadata findById(UUID id) {
        return fileMetadataRepository.findById(id).orElseThrow(() -> new NoSuchElementException(FILE_NOT_FOUND.formatted(id)));
    }

    @Transactional
    public FileResponse update(UUID id, UpdateFileRequest request) {
        FileMetadata fileMetadata = findById(id);
        fileMetadata = fileMetadata.update(request.fileName());
        return mapper.toFileResponse(fileMetadataRepository.save(fileMetadata));
    }

    @Transactional
    public void delete(UUID id, boolean isSoftDelete) {
        FileMetadata fileMetadata = findById(id);
        if (isSoftDelete) {
            fileMetadata.setStatus(FileStatus.DELETED);
            this.update(id, mapper.toUpdateFileRequest(fileMetadata));
        } else {
            fileMetadataRepository.delete(fileMetadata);
        }
    }
}
