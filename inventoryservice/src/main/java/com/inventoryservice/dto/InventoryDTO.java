package com.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;


@Component
@Schema(
		name = "Inventory",
		description = "This Inventory is used to hold stock availability data"
)
public class InventoryDTO {
	@Schema(
			description = "This is unique inventoryId",example = "1"
	)
	private Long inventoryId;
	@Schema(
			description = "inventoryCode(productName) is required",example = "Iphone 15"
	)
	@NotNull(message = "inventoryCode can't be empty")
	@Size(min = 4,message = "use minimum 4 character's for inventoryCode")
	@Size(max = 30,message = "inventoryCode length not more than 30 character's")
	private String inventoryCode;
	@Schema(
			description = "store dynamic productId",example = "64f53de0"
	)
	private String productId;
	@Schema(
			description = "storage is required",example = "128 Gb"
	)
	@NotNull(message = "storage can't be empty")
	@Size(min = 4,message = "use minimum 4 character's for storage")
	@Size(max = 7,message = "storage length not more than 7 character's")
	private String storage;
	@Schema(
			description = "inventoryQuantity is required",example = "351"
	)
	@NotNull(message = "inventoryQuantity can't be empty")
	@Min(value = 1,message = "Inventory quantity must be greater than or equal to 1")
	private Integer inventoryQuantity;

	public InventoryDTO(InventoryDTOBuilder inventoryDTOBuilder) {
		this.inventoryId = inventoryDTOBuilder.inventoryId;
		this.inventoryCode = inventoryDTOBuilder.inventoryCode;
		this.productId = inventoryDTOBuilder.productId;
		this.storage = inventoryDTOBuilder.storage;
		this.inventoryQuantity = inventoryDTOBuilder.inventoryQuantity;
	}

	public InventoryDTO(){

	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public String getInventoryCode() {
		return inventoryCode;
	}

	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}

	public String getProductId() { return productId;}

	public  String getStorage(){ return storage; }

	public static class InventoryDTOBuilder{
		private Long inventoryId;
		@NotNull(message = "inventoryCode can't be empty")
		@Size(min = 4,message = "use minimum 4 character's for inventoryCode")
		@Size(max = 30,message = "inventoryCode length not more than 7 character's")
		private String inventoryCode;
		private String productId;
		@NotNull(message = "storage can't be empty")
		@Size(min = 4,message = "use minimum 4 character's for storage")
		@Size(max = 7,message = "storage length not more than 7 character's")
		private String storage;
		@NotNull(message = "inventoryQuantity can't be empty")
		@Min(value = 1,message = "Inventory quantity must be greater than or equal to 1")
		private Integer inventoryQuantity;

		public InventoryDTOBuilder(){

		}

		public InventoryDTOBuilder setInventoryId(Long inventoryId) {
			this.inventoryId = inventoryId;
			return this;
		}

		public InventoryDTOBuilder setInventoryCode(String inventoryCode) {
			this.inventoryCode = inventoryCode;
			return this;
		}

		public InventoryDTOBuilder setProductId(String productId) {
			this.productId = productId; return this;
		}

		public  InventoryDTOBuilder setStorage(String storage){
			this.storage = storage; return this;
		}
		public InventoryDTOBuilder setInventoryQuantity(Integer inventoryQuantity) {
			this.inventoryQuantity = inventoryQuantity;
			return this;
		}

		public InventoryDTO build(){
			return new InventoryDTO(this);
		}
	}
}
