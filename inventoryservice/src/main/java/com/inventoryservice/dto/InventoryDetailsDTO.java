package com.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class InventoryDetailsDTO {
    private Long inventoryId;
    @NotNull(message = "inventoryCode can't be empty")
    @Size(min = 4,message = "use minimum 4 character's for inventoryCode")
    @Size(max = 30,message = "inventoryCode length not more than 7 character's")
    private String inventoryCode;
    @NotNull(message = "productId can't be empty")
    private String productId;
    @NotNull(message = "storage can't be empty")
    @Size(min = 4,message = "use minimum 4 character's for storage")
    @Size(max = 7,message = "storage length not more than 7 character's")
    private String storage;
    @NotNull(message = "inventoryQuantity can't be empty")
    @Min(value = 1,message = "Inventory quantity must be greater than or equal to 1")
    private Integer inventoryQuantity;
    @NotNull(message = "singleUnitPrice can't be empty")
    @Min(value = 4999,message = "singleUnitPrice must be greater than or equal to 4999")
    private BigDecimal singleUnitPrice;

    public InventoryDetailsDTO(InventoryDetailsDTOBuilder dtoBuilder) {
        this.inventoryId = dtoBuilder.inventoryId;
        this.inventoryCode = dtoBuilder.inventoryCode;
        this.productId = dtoBuilder.productId;
        this.storage = dtoBuilder.storage;
        this.inventoryQuantity = dtoBuilder.inventoryQuantity;
        this.singleUnitPrice = dtoBuilder.singleUnitPrice;
    }

    public InventoryDetailsDTO(){}
    public Long getInventoryId() {
        return inventoryId;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public String getProductId() {
        return productId;
    }

    public String getStorage() {
        return storage;
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public BigDecimal getSingleUnitPrice() {
        return singleUnitPrice;
    }

    public static class InventoryDetailsDTOBuilder{
        private Long inventoryId;
        @NotNull(message = "inventoryCode can't be empty")
        @Size(min = 4,message = "use minimum 4 character's for inventoryCode")
        @Size(max = 30,message = "inventoryCode length not more than 7 character's")
        private String inventoryCode;
        @NotNull(message = "productId can't be empty")
        private String productId;
        @NotNull(message = "storage can't be empty")
        @Size(min = 4,message = "use minimum 4 character's for storage")
        @Size(max = 7,message = "storage length not more than 7 character's")
        private String storage;
        @NotNull(message = "inventoryQuantity can't be empty")
        @Min(value = 1,message = "Inventory quantity must be greater than or equal to 1")
        private Integer inventoryQuantity;
        @NotNull(message = "singleUnitPrice can't be empty")
        @Min(value = 4999,message = "singleUnitPrice must be greater than or equal to 4999")
        private BigDecimal singleUnitPrice;

        public  InventoryDetailsDTOBuilder(){}

        public InventoryDetailsDTOBuilder setInventoryId(Long inventoryId) {
            this.inventoryId = inventoryId;return this;
        }

        public InventoryDetailsDTOBuilder setInventoryCode(String inventoryCode) {
            this.inventoryCode = inventoryCode;return this;
        }

        public InventoryDetailsDTOBuilder setProductId(String productId) {
            this.productId = productId;return this;
        }

        public InventoryDetailsDTOBuilder setStorage(String storage) {
            this.storage = storage;return this;
        }

        public InventoryDetailsDTOBuilder setInventoryQuantity(Integer inventoryQuantity) {
            this.inventoryQuantity = inventoryQuantity;return this;
        }

        public InventoryDetailsDTOBuilder setSingleUnitPrice(BigDecimal singleUnitPrice) {
            this.singleUnitPrice = singleUnitPrice;return this;
        }

        public InventoryDetailsDTO build(){
            return new InventoryDetailsDTO(this);
        }
    }
}
