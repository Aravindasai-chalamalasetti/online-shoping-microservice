package com.microservice.service;

import com.microservice.database.ProductDatabase;
import com.microservice.database.RamDetailsRepository;
import com.microservice.database.RamTypeRepository;
import com.microservice.database.StorageTypeRepository;
import com.microservice.dto.ExceptionDTO;
import com.microservice.dto.ProductCodeDTO;
import com.microservice.dto.ProductDTO;
import com.microservice.model.Product;
import com.microservice.model.RamDetails;
import com.microservice.model.RamType;
import com.microservice.model.StorageType;
import com.microservice.serviceDTO.ProductServiceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductService {

    public ProductService(RamDetailsRepository ramDetailsRepository, RamTypeRepository ramTypeRepository, StorageTypeRepository storageTypeRepository, ProductDatabase repo, ProductServiceDTO mapper) {
        this.ramDetailsRepository = ramDetailsRepository;
        this.ramTypeRepository = ramTypeRepository;
        this.storageTypeRepository = storageTypeRepository;
        this.repo = repo;
        this.mapper = mapper;
    }
   private static final Logger log =  Logger.getLogger(ProductService.class.getName());

	private final RamDetailsRepository ramDetailsRepository;
	private final RamTypeRepository ramTypeRepository;
	private final StorageTypeRepository storageTypeRepository;
	private final ProductDatabase repo;

	private final ProductServiceDTO mapper;

    public Product addProduct(ProductDTO productDetails) {
		Product prod = mapper.convertDtoToProduct(productDetails);
		RamDetails rd = ramDetailsRepository.findById(productDetails.getRamDetails().getRamId()).orElseThrow(null);
//				.orElseThrow(() -> new RuntimeException("RamDetails Not Found"));

		RamType rt = ramTypeRepository.findById(productDetails.getRamType().getRamTypeId()).orElseThrow(null);
//				.orElseThrow(() -> new RuntimeException("RamType not found"));

		StorageType st = storageTypeRepository.findById(productDetails.getStorageType().getStorageTypeId()).orElseThrow(null);
//				.orElseThrow(() -> new RuntimeException("StorageType not found"));
		Product p = repo.findByProductNameAndStorageCapacity(prod.getProductName(), prod.getStorageCapacity());
		if(prod != null) {
			prod.setRamDetails(rd != null ? rd : null);
			prod.setRamType(rt != null ? rt : null);
			prod.setStorageType(st != null ? st : null);
			if( prod.getProductId() == null && p == null) {
				repo.save(prod);
			}else if (p != null && p.getProductId() != null && prod.getProductId() != null && p.getProductId().equals(prod.getProductId())) {
				repo.save(prod);
			}
		}else{
			throw new ExceptionDTO("Product id " + prod.getProductId() + "is not exist",new Date(), HttpStatus.NOT_FOUND,null);
		}
		log.info("product {} is saved successfully" + prod.getProductId());
		return prod;
	}

	public List<ProductDTO> fetchProductList() {
		// List<ProductDTO> prod = mapper.getProductToDtoList( repo.findAll());
		return mapper.getProductToDtoList(repo.findAll());
	}

	public ProductDTO findProductNameWithStorage(ProductCodeDTO productCodeDTO){
		ProductDTO productDTO = new ProductDTO();
		if(productCodeDTO != null && productCodeDTO.getProductName() != null && productCodeDTO.getStorageCapacity() != null) {
			Product p = repo.findByProductNameAndStorageCapacity(productCodeDTO.getProductName(), productCodeDTO.getStorageCapacity());
			if(p != null && p.getProductId() != null){
				productDTO = mapper.convertProductToDto(p);
			}

		}return productDTO;
		}

	public ProductDTO findProductById(String productId){
		Product product = repo.findById(productId).orElseThrow(
				()->new ExceptionDTO("Product id " + productId + "is not exist",new Date(), HttpStatus.NOT_FOUND,null)
		);
		return mapper.convertProductToDto(product);
	}

	public String deleteProduct(String productId){
		Product product = repo.findById(productId).orElseThrow(
				()->new ExceptionDTO("Product id " + productId + "is not exist",new Date(), HttpStatus.NOT_FOUND,null)
		);
		String message = "";
		if(product != null && product.getProductId() != null){
			repo.delete(product);
			message = "Successfully product is deleted";
		}else{
			message = "Unable to delete Product because" + productId + " id is not exist";
		}
		return message;
	}
}
