package com.aviatickets.storage.service;

import com.aviatickets.storage.exception.UnsupportedExtensionException;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

@RequiredArgsConstructor
@Getter
public enum SupportedExtension {

    // Image extensions
    JPEG("jpeg", "image/jpeg"),
    JPG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    BMP("bmp", "image/bmp"),
    WEBP("webp", "image/webp"),
    TIFF("tiff", "image/tiff"),

    // Document extensions
    PDF("pdf", "application/pdf"),
    DOC("doc", "application/msword"),
    DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    XLS("xls", "application/vnd.ms-excel"),
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    PPT("ppt", "application/vnd.ms-powerpoint"),
    PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    TXT("txt", "text/plain"),
    RTF("rtf", "application/rtf"),
    CSV("csv", "text/csv"),
    JSON("json", "application/json"),
    XML("xml", "application/xml"),
    HTML("html", "text/html");

    public static final String UNSUPPORTED_EXTENSION = "Unsupported extension: ";

    private final String extension;
    private final String mimeType;

    public static SupportedExtension from(@NotBlank String fileName) {
        String extension = getFileExtension(fileName);

        if (ObjectUtils.isEmpty(extension)) {
           throw new UnsupportedExtensionException(UNSUPPORTED_EXTENSION + fileName);
        }
        for (SupportedExtension supportedExtension : values()) {
            if (supportedExtension.getExtension().equalsIgnoreCase(extension)) {
                return supportedExtension;
            }
        }
        throw new UnsupportedExtensionException(UNSUPPORTED_EXTENSION + fileName);
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
