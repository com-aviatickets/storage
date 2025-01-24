package com.aviatickets.storage.mapper;

import com.aviatickets.storage.controller.response.FileResponse;
import com.aviatickets.storage.model.FileMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "extension", expression = "java(fileMetadata.getExtension().getExtension())")
    @Mapping(target = "mimeType", expression = "java(fileMetadata.getExtension().getMimeType())")
    @Mapping(target = "uploadedAt", expression = "java(fileMetadata.getUploadedAt())")
    @Mapping(target = "lastModifiedAt", expression = "java(fileMetadata.getLastModifiedAt())")
    FileResponse toFileResponse(FileMetadata fileMetadata);

}
