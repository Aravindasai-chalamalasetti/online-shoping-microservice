package com.inventoryservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class ProductCodeDTO {

    @NotNull(message = "productName can't be empty")
    @Size(min = 4,message = "use minimum 4 character's for productName")
    @Size(max = 35,message = "productName length not more than 35 character's")
    private String productName;
    @NotEmpty(message = "storageCapacity can't be empty")
    @Size(min = 4,message = "use minimum 4 character's for storageCapacity")
    @Size(max = 7,message = "storageCapacity length not more than 7 character's")
    private String storageCapacity;
    @NotEmpty(message = "RAM Size can't be empty")
    @Pattern(
            regexp = "^(?i)(8 Gb|12 Gb|16 Gb|24 Gb|32 Gb|64 Gb|128 Gb)$",
            message = "ramSize must be 8 Gb, 12 Gb, 16 Gb, ..."
    )
    @Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
    private String ramSize;

    public ProductCodeDTO(String productName, String storageCapacity,String ramSize) {
        this.productName = productName;
        this.storageCapacity = storageCapacity;
        this.ramSize = ramSize;
    }

    public ProductCodeDTO(){}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getRamSize() {
        return ramSize;
    }

    public void setRamSize(String ramSize) {
        this.ramSize = ramSize;
    }
}
