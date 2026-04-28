package com.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InventoryCodeDTO {
    @JsonProperty("inventoryCode")
    private String inventoryCode;
    @JsonProperty("isInStock")
    private boolean inStock;
    @JsonProperty("inventoryId")
    private Long inventoryId;
    @JsonProperty("storage")
    private String storage;
    @JsonProperty("singleUnitPrice")
    private BigDecimal singleUnitPrice;
    @JsonProperty("ramSize")
    private String ramSize;

    public InventoryCodeDTO(InventoryCodeDTOBuilder inv) {
        this.inventoryCode = inv.inventoryCode;
        this.inStock = inv.inStock;
        this.inventoryId = inv.inventoryId;
        this.storage = inv.storage;
        this.singleUnitPrice = inv.singleUnitPrice;
        this.ramSize = inv.ramSize;
    }

    public InventoryCodeDTO(){}

    public String getInventoryCode() {
        return inventoryCode;
    }

    public Long getInventoryId() { return inventoryId; }

    public boolean isInStock() {
        return inStock;
    }

    public String getStorage(){ return storage; }

    public BigDecimal getSingleUnitPrice(){return singleUnitPrice;}

    public String getRamSize() {
        return ramSize;
    }

    public static class InventoryCodeDTOBuilder{
        private String inventoryCode;
        private boolean inStock;
        private Long inventoryId;
        private String storage;
        private BigDecimal singleUnitPrice;
        private String ramSize;

        public InventoryCodeDTOBuilder(){}
        public InventoryCodeDTOBuilder setInventoryCode(String inventoryCode) {
            this.inventoryCode = inventoryCode;
            return this;
        }

        public InventoryCodeDTOBuilder setInStock(boolean inStock) {
            this.inStock = inStock;
            return this;
        }

        public InventoryCodeDTOBuilder setInventoryId(Long inventoryId) {
            this.inventoryId = inventoryId;
            return this;
        }

        public InventoryCodeDTOBuilder setStorage(String storage){
            this.storage = storage;
            return this;
        }

        public InventoryCodeDTOBuilder setSingleUnitPrice(BigDecimal singleUnitPrice){
            this.singleUnitPrice = singleUnitPrice; return  this;
        }

        public InventoryCodeDTOBuilder setRamSize(String ramSize) {
            this.ramSize = ramSize;return this;
        }

        public InventoryCodeDTO build(){
            return new InventoryCodeDTO(this);
        }
    }
}
