package com.aviatickets.storage.controller.response;

import java.time.ZonedDateTime;
import java.util.UUID;

public record FileResponse(
        UUID id,
        String fileName,
        String extension,
        Long size,
        String mimeType,
        ZonedDateTime uploadedAt,
        ZonedDateTime lastModifiedAt,
        String status
) {
}
