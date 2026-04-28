package com.microservice.serviceDTO;

import com.microservice.dto.ProductDTO;
import com.microservice.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductServiceDTO {

	private final RamDetailsServiceDTO ramDetailsServiceDTO;

	private final RamTypeServiceDTO ramTypeServiceDTO;

	private final StorageTypeServiceDTO storageTypeServiceDTO;

    public ProductServiceDTO(RamDetailsServiceDTO ramDetailsServiceDTO, RamTypeServiceDTO ramTypeServiceDTO, StorageTypeServiceDTO storageTypeServiceDTO) {
        this.ramDetailsServiceDTO = ramDetailsServiceDTO;
        this.ramTypeServiceDTO = ramTypeServiceDTO;
        this.storageTypeServiceDTO = storageTypeServiceDTO;
    }

    public Product convertDtoToProduct(ProductDTO productDetails) {
		Product prod = new Product();
		prod.setProductId(productDetails.getProductId());
		prod.setStorageCapacity(productDetails.getStorageCapacity());
		prod.setProductName(productDetails.getProductName());
		prod.setPrice(productDetails.getPrice());

		return prod;
	}

	public ProductDTO convertProductToDto(Product prod) {
		ProductDTO product = new ProductDTO.ProductDTOBuilder()
				.setProductId(prod.getProductId())
				.setStorageCapacity(prod.getStorageCapacity())
				.setProductName(prod.getProductName())
				.setPrice(prod.getPrice())
				.setRamDetails(ramDetailsServiceDTO.convertRamDetailsToDto(prod.getRamDetails()))
				.build();
		return product;
	}

	public List<ProductDTO> getProductToDtoList(List<Product> prod){
		List<ProductDTO> dtoList = prod.stream().map(p->mapProductToDto(p)).collect(Collectors.toList());
		return dtoList;
	}

	private ProductDTO mapProductToDto(Product prod) {
		ProductDTO dto = convertProductsToDto(prod);
		return dto;
	}

	public ProductDTO convertProductsToDto(Product prod) {
		ProductDTO product = new ProductDTO.ProductDTOBuilder()
				.setProductId(prod.getProductId())
				.setStorageCapacity(prod.getStorageCapacity())
				.setProductName(prod.getProductName())
				.setPrice(prod.getPrice())
				.setRamType(ramTypeServiceDTO.convertRamTypeToDto(prod.getRamType()))
				.setRamDetails(ramDetailsServiceDTO.convertRamDetailsToDto(prod.getRamDetails()))
				.setStorageType(storageTypeServiceDTO.convertStorageTypeToDto(prod.getStorageType()))
				.build();
		return product;
	}
}
