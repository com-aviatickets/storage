package com.aviatickets.storage.model;

import com.aviatickets.storage.service.SupportedExtension;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "storage_file_metadata")
@SQLDelete(sql = "UPDATE storage_file_metadata SET status = 'DELETED' WHERE id = ?")
@FilterDef(name = "deletedFileMetadata", parameters = @ParamDef(name = "DELETED", type = String.class))
@Filter(name = "deletedFileMetadata", condition = "status = :DELETED")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileEntity file;
    private String fileName;
    @Enumerated(EnumType.STRING)
    private SupportedExtension extension;
    @Enumerated(EnumType.STRING)
    private FileStatus status;
    private Long size;
    @CreationTimestamp
    private ZonedDateTime uploadedAt;
    @UpdateTimestamp
    private ZonedDateTime lastModifiedAt;

    public FileMetadata update(String fileName) {
        if (ObjectUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        SupportedExtension extension = SupportedExtension.from(fileName);
        if (!this.extension.equals(extension)) {
            throw new IllegalArgumentException("File extensions cannot be changed");
        }

        this.fileName = fileName;

        return this;
    }

}
