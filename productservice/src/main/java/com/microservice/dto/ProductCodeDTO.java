package com.microservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    public ProductCodeDTO(String productName, String storageCapacity) {
        this.productName = productName;
        this.storageCapacity = storageCapacity;
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
}
