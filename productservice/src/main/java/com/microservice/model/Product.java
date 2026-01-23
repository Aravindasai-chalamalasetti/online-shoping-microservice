package com.microservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Document(collection = "product-details")
public class Product {
    @Id
    private String productId;
    @NotEmpty(message = "storageCapacity can't be empty")
    @Size(min = 4,message = "use minimum 4 character's for storageCapacity")
    @Size(max = 7,message = "storageCapacity length not more than 7 character's")
    private String storageCapacity;
    @NotNull(message = "productName can't be empty")
    @Size(min = 4,message = "use minimum 4 character's for productName")
    @Size(max = 35,message = "productName length not more than 35 character's")
    private String productName;
    @NotNull(message = "price can't be empty")
    @Min(value = 4999,message = "Price must be greater than or equal to 4999")
    private BigDecimal price;
    @NotNull(message = "ramDetails can't be empty")
    @DBRef
    private RamDetails ramDetails;
    @NotNull(message = "ramType can't be empty")
    @DBRef
    private RamType ramType;
    @NotNull(message = "storageType can't be empty")
    @DBRef
    private StorageType storageType;


    public Product(String productId, String storageCapacity, String productName, BigDecimal price, RamDetails ramDetails, RamType ramType, StorageType storageType) {
        this.productId = productId;
        this.storageCapacity = storageCapacity;
        this.productName = productName;
        this.price = price;
        this.ramDetails = ramDetails;
        this.ramType = ramType;
        this.storageType = storageType;
    }

    public Product(){

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RamDetails getRamDetails() {
        return ramDetails;
    }

    public void setRamDetails(RamDetails ramDetails) {
        this.ramDetails = ramDetails;
    }

    public RamType getRamType() {
        return ramType;
    }

    public void setRamType(RamType ramType) {
        this.ramType = ramType;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }
}
