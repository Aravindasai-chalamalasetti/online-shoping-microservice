package com.inventoryservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name="inventorytable")
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public Inventory(Long inventoryId, String inventoryCode,String productId, String storage, Integer inventoryQuantity,BigDecimal singleUnitPrice) {
		this.inventoryId = inventoryId;
		this.inventoryCode = inventoryCode;
		this.productId = productId;
		this.storage = storage;
		this.inventoryQuantity = inventoryQuantity;
		this.singleUnitPrice = singleUnitPrice;
	}

	public Inventory(){

	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getInventoryCode() {
		return inventoryCode;
	}

	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}

	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(Integer inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public BigDecimal getSingleUnitPrice() {
		return singleUnitPrice;
	}

	public void setSingleUnitPrice(BigDecimal singleUnitPrice) {
		this.singleUnitPrice = singleUnitPrice;
	}
}
