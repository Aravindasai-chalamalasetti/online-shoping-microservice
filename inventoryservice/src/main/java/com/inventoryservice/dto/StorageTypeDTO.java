package com.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
@Schema(
        name = "StorageType",
        description = "This is hold StorageType information"
)
public class StorageTypeDTO {
    @Schema(
            description = "This is storageTypeId",example = "4"
    )
    private Long storageTypeId;
    @Schema(
            description = "This is storageType",example = "Optimum memory"
    )
    @NotEmpty(message = "Storage type can't be empty")
    @Size(min = 3, max = 16, message = "Storage type must be between 3 and 16 characters")
    private String storageType;

    public StorageTypeDTO(StorageTypeDTOBuilder storageTypeDTOBuilder) {
        this.storageTypeId = storageTypeDTOBuilder.storageTypeId;
        this.storageType = storageTypeDTOBuilder.storageType;
    }

    public StorageTypeDTO(){}

    public Long getStorageTypeId() {
        return storageTypeId;
    }

    public String getStorageType() {
        return storageType;
    }

    public static class StorageTypeDTOBuilder{
        private Long storageTypeId;
        @NotEmpty(message = "Storage type can't be empty")
        @Size(min = 3, max = 16, message = "Storage type must be between 3 and 16 characters")
        private String storageType;
        public StorageTypeDTOBuilder(){}

        public StorageTypeDTOBuilder setStorageTypeId(Long storageTypeId) {
            this.storageTypeId = storageTypeId;return this;
        }

        public StorageTypeDTOBuilder setStorageType(String storageType) {
            this.storageType = storageType;return this;
        }

        public StorageTypeDTO build(){return new StorageTypeDTO(this);}
    }
}

