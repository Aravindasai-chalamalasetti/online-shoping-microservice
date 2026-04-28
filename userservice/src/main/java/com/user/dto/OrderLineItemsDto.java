package com.user.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderLineItemsDto {
	private Long itemId;
	@NotNull(message = "Product name cannot be empty")
	private String skuCode;
	private BigDecimal itemPrice;
	@NotNull(message = "Quantity should not be empty")
	private Integer itemQuantity;
	@NotEmpty(message = "Storage cannot be empty")
	private String storage;
	private Long inventoryId;
	@NotEmpty(message = "RAM Size can't be empty")
	@Pattern(
			regexp = "^(?i)(8 Gb|12 Gb|16 Gb|24 Gb|32 Gb|64 Gb|128 Gb)$",
			message = "ramSize must be 8 Gb, 12 Gb, 16 Gb, ..."
	)
	@Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
	private String ramSize;

	public OrderLineItemsDto(OrderLineItemsDtoBuilder orderLineItemsBuilder) {
		this.itemId = orderLineItemsBuilder.itemId;
		this.skuCode = orderLineItemsBuilder.skuCode;
		this.itemPrice = orderLineItemsBuilder.itemPrice;
		this.itemQuantity = orderLineItemsBuilder.itemQuantity;
		this.storage = orderLineItemsBuilder.storage;
		this.inventoryId = orderLineItemsBuilder.inventoryId;
		this.ramSize = orderLineItemsBuilder.ramSize;
	}

	public OrderLineItemsDto() {}

	public Long getItemId() {
		return itemId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public String getStorage(){return storage;}

	public Long getInventoryId(){return inventoryId;}

	public String getRamSize() {
		return ramSize;
	}

	@Override
	public String toString() {
		return "OrderLineItemsDto{" +
				"itemId=" + itemId +
				", skuCode='" + skuCode + '\'' +
				", itemPrice=" + itemPrice +
				", itemQuantity=" + itemQuantity +
				", storage='" + storage + '\'' +
				", inventoryId='" + inventoryId + '\'' +
				", ramSize='" + ramSize + '\'' +
				'}';
	}

	public static class OrderLineItemsDtoBuilder {
		private Long itemId;
		@NotNull(message = "Product name cannot be empty")
		private String skuCode;
		private BigDecimal itemPrice;
		@NotNull(message = "Quantity should not be empty")
		private Integer itemQuantity;
		@NotEmpty(message = "Storage cannot be empty")
		private String storage;
		private Long inventoryId;
		@NotEmpty(message = "RAM Size can't be empty")
		@Pattern(
				regexp = "^(?i)(8 Gb|12 Gb|16 Gb|24 Gb|32 Gb|64 Gb|128 Gb)$",
				message = "ramSize must be 8 Gb, 12 Gb, 16 Gb, ..."
		)
		@Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
		private String ramSize;
		public OrderLineItemsDtoBuilder() {}

		public OrderLineItemsDtoBuilder setItemId(Long itemId) {
			this.itemId = itemId;
			return this;
		}

		public OrderLineItemsDtoBuilder setSkuCode(String skuCode) {
			this.skuCode = skuCode;
			return this;
		}

		public OrderLineItemsDtoBuilder setItemPrice(BigDecimal itemPrice) {
			this.itemPrice = itemPrice;
			return this;
		}

		public OrderLineItemsDtoBuilder setItemQuantity(Integer itemQuantity) {
			this.itemQuantity = itemQuantity;
			return this;
		}

		public OrderLineItemsDtoBuilder setStorage(String storage){
			this.storage = storage;return this;
		}

		public OrderLineItemsDtoBuilder setInventoryId(Long inventoryId){
			this.inventoryId = inventoryId;return  this;
		}

		public OrderLineItemsDtoBuilder setRamSize(String ramSize) {
			this.ramSize = ramSize;return this;
		}

		public OrderLineItemsDto build() {
			return new OrderLineItemsDto(this);
		}
	}
}