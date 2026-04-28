package com.orderservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name="order-line-items")
public class OrderLineItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	@NotNull(message = "Product name cannot be empty")
	private String skuCode;
	@NotNull(message = "Price cannot be empty")
	@Min(value = 100,message = "Item price should be minimum 100 rupees")
	private BigDecimal itemPrice;
	@NotNull(message = "Quantity should not be empty")
	@Min(value = 1, message = "Minimum one quantity is required for placing order")
	private Integer itemQuantity;
	@NotEmpty(message = "Storage cannot be empty")
	@Size(min = 4,message = "use minimum 4 character's for storage")
	@Size(max = 7,message = "storage length not more than 7 character's")
	private String storage;
	@NotNull(message = "inventoryId cannot be empty")
	private Long inventoryId;
	@NotEmpty(message = "RAM Size can't be empty")
	@Pattern(
			regexp = "^(?i)(8 Gb|12 Gb|16 Gb|24 Gb|32 Gb|64 Gb|128 Gb)$",
			message = "ramSize must be 8 Gb, 12 Gb, 16 Gb, ..."
	)
	@Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
	private String ramSize;
	public OrderLineItems(Long itemId, String itemCode, BigDecimal itemPrice, Integer itemQuantity,String storage,Long inventoryId,String ramSize) {
		this.itemId = itemId;
		this.skuCode = itemCode;
		this.itemPrice = itemPrice;
		this.itemQuantity = itemQuantity;
		this.storage = storage;
		this.inventoryId = inventoryId;
		this.ramSize = ramSize;
	}

	public OrderLineItems(){

	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getRamSize() {
		return ramSize;
	}

	public void setRamSize(String ramSize) {
		this.ramSize = ramSize;
	}
}
