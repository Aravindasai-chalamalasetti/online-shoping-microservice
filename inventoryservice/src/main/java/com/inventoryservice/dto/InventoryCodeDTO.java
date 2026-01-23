package com.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Schema(
        name = "InventoryCode",
        description = "This InventoryCode is used for fetch inventory custom data"
)
public class InventoryCodeDTO {
    @Schema(
            description = "InventoryCode(productName) is required",example = "Sansung s25 ultra"
    )
    private String inventoryCode;
    @Schema(
            description = "isInStock is available or not",example = "stock available"
    )
    private boolean isInStock;
    @Schema(
            description = "inventoryId is required"
    )
    private Long inventoryId;
    @Schema(
            description = "storage is required",example = "512 Gb"
    )
    private String storage;
    @Schema(
            description = "SingleUnitPrice is required",example = "Rs : 149999/-"
    )
    private BigDecimal singleUnitPrice;

    public InventoryCodeDTO(InventoryCodeDTOBuilder inv) {
        this.inventoryCode = inv.inventoryCode;
        this.isInStock = inv.isInStock;
        this.inventoryId = inv.inventoryId;
        this.storage = inv.storage;
        this.singleUnitPrice = inv.singleUnitPrice;
    }

    public InventoryCodeDTO(){}
    @JsonProperty("inventoryCode")
    public String getInventoryCode() {
        return inventoryCode;
    }
    @JsonProperty("isInStock")
    public boolean getIsInStock() {
        return isInStock;
    }
    @JsonProperty("inventoryId")
    public Long getInventoryId(){ return inventoryId; }
    @JsonProperty("storage")
    public String getStorage(){ return storage; }
    @JsonProperty("singleUnitPrice")
    public BigDecimal getSingleUnitPrice(){return singleUnitPrice;}

    public static class InventoryCodeDTOBuilder{
        private String inventoryCode;
        private boolean isInStock;
        private Long inventoryId;
        private String storage;
        private BigDecimal singleUnitPrice;

        public InventoryCodeDTOBuilder(){}
        public InventoryCodeDTOBuilder setInventoryCode(String inventoryCode) {
            this.inventoryCode = inventoryCode;
            return this;
        }

        public InventoryCodeDTOBuilder setInStock(boolean inStock) {
            this.isInStock = inStock;
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
        public InventoryCodeDTO build(){
            return new InventoryCodeDTO(this);
        }
    }
}
