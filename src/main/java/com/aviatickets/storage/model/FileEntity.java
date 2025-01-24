package com.aviatickets.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "storage_file")
@NoArgsConstructor
@Getter
@Setter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_file_seq")
    @SequenceGenerator(
            name = "storage_file_seq",
            sequenceName = "storage_file_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false, name = "data", columnDefinition = "bytea")
    private byte[] data;

    public FileEntity(byte[] data) {
        this.data = data;
    }

}
