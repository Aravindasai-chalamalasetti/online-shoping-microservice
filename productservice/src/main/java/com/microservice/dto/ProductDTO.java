package com.microservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductDTO {
	private String productId;
	@NotEmpty(message = "storageCapacity can't be empty")
	@Size(min = 4,message = "use minimum 4 character's for storageCapacity")
	@Size(max = 7,message = "storageCapacity length not more than 7 character's")
	private String storageCapacity;
	@NotNull(message = "productName can't be empty")
	@Size(min = 4,message = "use minimum 4 character's for productName")
	@Size(max = 35,message = "productName length not more than 35 character's")
	private String productName;
	@NotNull(message = "product price can't be empty")
	@Min(value = 4999,message = "Price must be greater than or equal to 4999")
	private BigDecimal price;
	@NotNull(message = "ramDetails can't be empty")
	private RamDetailsDTO ramDetails;
	@NotNull(message = "ramType can't be empty")
	private RamTypeDTO ramType;
	@NotNull(message = "storageType can't be empty")
	private StorageTypeDTO storageType;

	public ProductDTO(ProductDTOBuilder productDTOBuilder) {
		this.productId = productDTOBuilder.productId;
		this.storageCapacity = productDTOBuilder.storageCapacity;
		this.productName = productDTOBuilder.productName;
		this.price = productDTOBuilder.price;
		this.ramDetails = productDTOBuilder.ramDetails;
		this.ramType = productDTOBuilder.ramType;
		this.storageType = productDTOBuilder.storageType;
	}

	public ProductDTO() {

	}

	public String getProductId() {
		return productId;
	}

	public String getStorageCapacity() {
		return storageCapacity;
	}

	public String getProductName() {
		return productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public RamDetailsDTO getRamDetails() {
		return ramDetails;
	}

	public RamTypeDTO getRamType() {
		return ramType;
	}

	public StorageTypeDTO getStorageType() {
		return storageType;
	}

	public static class ProductDTOBuilder{
		private String productId;
		@NotEmpty(message = "storageCapacity can't be empty")
		@Size(min = 4,message = "use minimum 4 character's for storageCapacity")
		@Size(max = 7,message = "storageCapacity length not more than 7 character's")
		private String storageCapacity;
		@NotNull(message = "productName can't be empty")
		@Size(min = 4,message = "use minimum 5 character's for productName")
		@Size(max = 35,message = "productName length not more than 35 character's")
		private String productName;
		@NotNull(message = "product price can't be empty")
		@Min(value = 4999,message = "Price must be greater than or equal to 4999")
		private BigDecimal price;
		@NotNull(message = "ramDetails can't be empty")
		private RamDetailsDTO ramDetails;
		@NotNull(message = "ramType can't be empty")
		private RamTypeDTO ramType;
		@NotNull(message = "storageType can't be empty")
		private StorageTypeDTO storageType;

		public ProductDTOBuilder(){

		}

		public ProductDTOBuilder setProductId(String productId) {
			this.productId = productId;
			return this;
		}

		public ProductDTOBuilder setStorageCapacity(String storageCapacity) {
			this.storageCapacity = storageCapacity;
			return this;
		}

		public ProductDTOBuilder setProductName(String productName) {
			this.productName = productName;
			return this;
		}

		public ProductDTOBuilder setPrice(BigDecimal price) {
			this.price = price;
			return this;
		}

		public ProductDTOBuilder setRamDetails(RamDetailsDTO ramDetails) {
			this.ramDetails = ramDetails;return this;
		}

		public ProductDTOBuilder setRamType(RamTypeDTO ramType) {
			this.ramType = ramType;return this;
		}

		public ProductDTOBuilder setStorageType(StorageTypeDTO storageType) {
			this.storageType = storageType;return this;
		}

		public ProductDTO build(){
			return new ProductDTO(this);
		}
	}
}
