package com.aviatickets.storage.model;

import com.aviatickets.storage.service.SupportedExtension;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "storage_file_metadata")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private FileEntity file;
    private String fileName;
    @Enumerated(EnumType.STRING)
    private SupportedExtension extension;
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
