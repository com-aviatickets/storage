package com.aviatickets.storage.controller;

import com.aviatickets.storage.controller.request.UpdateFileRequest;
import com.aviatickets.storage.controller.response.FileResponse;
import com.aviatickets.storage.model.FileMetadata;
import com.aviatickets.storage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/internal/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public FileResponse uploadFile(@RequestPart("file") MultipartFile file) {
        return fileService.upload(file);
    }

    @GetMapping("/{id}/metadata")
    public FileResponse getFileMetadata(@PathVariable UUID id) {
        return fileService.getMetadataById(id);
    }

    @PatchMapping("/{id}/update")
    public FileResponse update(@PathVariable UUID id, @RequestBody UpdateFileRequest request) {
        return fileService.update(id, request);
    }

    @GetMapping("download/{id}")
    public ResponseEntity<StreamingResponseBody> downloadFile(@PathVariable UUID id) {
        FileMetadata fileMetadata = fileService.findById(id);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.valueOf(fileMetadata.getExtension().getMimeType()));
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .name(fileMetadata.getFileName())
                        .filename(fileMetadata.getFileName(), StandardCharsets.UTF_8)
                        .build()
        );

        StreamingResponseBody streamingResponseBody = outputStream -> {
            outputStream.write(fileMetadata.getFile().getData());
            outputStream.flush();
        };

        return ResponseEntity.ok().headers(headers).body(streamingResponseBody);
    }

}
