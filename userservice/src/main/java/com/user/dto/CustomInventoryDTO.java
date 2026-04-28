package com.user.dto;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CustomInventoryDTO {
    @NotNull(message = "inventoryCode can't be empty")
    @Size(min = 4,message = "use minimum 4 character's for inventoryCode")
    @Size(max = 30,message = "inventoryCode length not more than 7 character's")
    private String inventoryCode;
    @NotNull(message = "storage can't be empty")
    @Size(min = 4,message = "use minimum 4 character's for storage")
    @Size(max = 7,message = "storage length not more than 7 character's")
    private String storage;
    @NotNull(message = "singleUnitPrice can't be empty")
    @Min(value = 4999,message = "singleUnitPrice must be greater than or equal to 4999")
    private BigDecimal singleUnitPrice;
    @NotEmpty(message = "RAM Size can't be empty")
    @Pattern(
            regexp = "^(?i)(8 Gb|12 Gb|16 Gb|24 Gb|32 Gb|64 Gb|128 Gb)$",
            message = "ramSize must be 8 Gb, 12 Gb, 16 Gb, ..."
    )
    @Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
    private String ramSize;

    public CustomInventoryDTO(CustomInventoryDTOBuilder dtoBuilder) {
        this.inventoryCode = dtoBuilder.inventoryCode;
        this.storage = dtoBuilder.storage;
        this.singleUnitPrice = dtoBuilder.singleUnitPrice;
        this.ramSize = dtoBuilder.ramSize;
    }

    public CustomInventoryDTO(){}

    public String getInventoryCode() {
        return inventoryCode;
    }

    public String getStorage() {
        return storage;
    }

    public BigDecimal getSingleUnitPrice() {
        return singleUnitPrice;
    }

    public String getRamSize() {
        return ramSize;
    }

    public static class CustomInventoryDTOBuilder{
        @NotNull(message = "inventoryCode can't be empty")
        @Size(min = 4,message = "use minimum 4 character's for inventoryCode")
        @Size(max = 30,message = "inventoryCode length not more than 7 character's")
        private String inventoryCode;
        @NotNull(message = "storage can't be empty")
        @Size(min = 4,message = "use minimum 4 character's for storage")
        @Size(max = 7,message = "storage length not more than 7 character's")
        private String storage;
        @NotNull(message = "singleUnitPrice can't be empty")
        @Min(value = 4999,message = "singleUnitPrice must be greater than or equal to 4999")
        private BigDecimal singleUnitPrice;
        @NotEmpty(message = "RAM Size can't be empty")
        @Pattern(
                regexp = "^(?i)(8 Gb|12 Gb|16 Gb|24 Gb|32 Gb|64 Gb|128 Gb)$",
                message = "ramSize must be 8 Gb, 12 Gb, 16 Gb, ..."
        )
        @Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
        private String ramSize;
        public CustomInventoryDTOBuilder(){}

        public CustomInventoryDTOBuilder setInventoryCode(String inventoryCode) {
            this.inventoryCode = inventoryCode;return this;
        }

        public CustomInventoryDTOBuilder setStorage(String storage) {
            this.storage = storage;return this;
        }

        public CustomInventoryDTOBuilder setSingleUnitPrice(BigDecimal singleUnitPrice) {
            this.singleUnitPrice = singleUnitPrice;return this;
        }

        public CustomInventoryDTOBuilder setRamSize(String ramSize) {
            this.ramSize = ramSize;return this;
        }

        public CustomInventoryDTO build(){
            return new CustomInventoryDTO(this);
        }
    }
}
