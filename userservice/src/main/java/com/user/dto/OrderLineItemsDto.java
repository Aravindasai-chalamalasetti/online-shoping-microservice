package com.user.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	private String inventoryId;

	public OrderLineItemsDto(OrderLineItemsDtoBuilder orderLineItemsBuilder) {
		this.itemId = orderLineItemsBuilder.itemId;
		this.skuCode = orderLineItemsBuilder.skuCode;
		this.itemPrice = orderLineItemsBuilder.itemPrice;
		this.itemQuantity = orderLineItemsBuilder.itemQuantity;
		this.storage = orderLineItemsBuilder.storage;
		this.inventoryId = orderLineItemsBuilder.inventoryId;
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

	public String getInventoryId(){return inventoryId;}

	@Override
	public String toString() {
		return "OrderLineItemsDto{" +
				"itemId=" + itemId +
				", skuCode='" + skuCode + '\'' +
				", itemPrice=" + itemPrice +
				", itemQuantity=" + itemQuantity +
				", storage='" + storage + '\'' +
				", inventoryId='" + inventoryId + '\'' +
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
		private String inventoryId;

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

		public OrderLineItemsDtoBuilder setInventoryId(String inventoryId){
			this.inventoryId = inventoryId;return  this;
		}
		public OrderLineItemsDto build() {
			return new OrderLineItemsDto(this);
		}
	}
}