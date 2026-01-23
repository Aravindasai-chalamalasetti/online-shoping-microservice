package com.microservice.model;

import com.microservice.sequence.SequenceGeneratorService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "storage-type-details")
public class StorageType {
    @Id
    private Long storageTypeId;
    @NotEmpty(message = "Storage type can't be empty")
    @Size(min = 3, max = 16, message = "Storage type must be between 3 and 16 characters")
    private String storageType;  // e.g., "Mobile Storage", "SSD", "HDD", "Optimum memory"

    public StorageType(Long storageTypeId,String storageType) {
        this.storageTypeId = storageTypeId;
        this.storageType = storageType;
    }
    public void generateIdIfNeeded(SequenceGeneratorService sequenceGenerator) {
        if (this.storageTypeId == null) {
            this.storageTypeId = sequenceGenerator.generateSequence("storage-type-details_seq");
        }
    }
    public StorageType(){}
    public Long getStorageTypeId() { return storageTypeId; }
    public void setStorageTypeId(Long storageTypeId) { this.storageTypeId = storageTypeId; }
    public String getStorageType() { return storageType; }
    public void setStorageType(String storageType) { this.storageType = storageType; }

}
