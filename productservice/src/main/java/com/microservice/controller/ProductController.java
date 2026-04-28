package com.microservice.controller;

import com.microservice.dto.ProductCodeDTO;
import com.microservice.dto.ProductDTO;
import com.microservice.model.Product;
import com.microservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
		name = "CRUD REST APIs for Product",
		description = "CRUD REST APIs in Product to CREATE,FETCH,UPDATE and DELETE product details"
)
@RestController
@RequestMapping("/product")
public class ProductController {

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

	private final ProductService productService;

	@Operation(
			summary = "Create Product REST API",
			description = "REST API to create new Product"
	)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP status created"
	)
    @PostMapping("/addProduct")
	@ResponseStatus(HttpStatus.CREATED)
	public Product addProduct(@Valid @RequestBody ProductDTO product) {
		return productService.addProduct(product);
	}

	@Operation(
			summary = "Fetch Product REST API",
			description = "REST API to fetch Product details list"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status Ok"
	)
	@GetMapping("/fetchProductsList")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<ProductDTO> fetchAllProducts(){
		return productService.fetchProductList();
	}

	@Operation(
			summary = "Fetch Product details REST API",
			description = "REST API to fetch Product details based on Product name & storage"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status Ok"
	)
	@PostMapping("/fetchProductData")
	@ResponseStatus(HttpStatus.CREATED)
    public ProductDTO fetchProductData(@Valid @RequestBody ProductCodeDTO productCodeDTO){
		return productService.findProductNameWithStorage(productCodeDTO);
	}

	@PostMapping("/fetchProductsData")
	@ResponseStatus(HttpStatus.CREATED)
	public List<ProductDTO> fetchProductsData(@Valid @RequestBody ProductCodeDTO productCodeDTO){
		return productService.findProductWithStorageList(productCodeDTO);
	}

	@GetMapping("/fetchData")
	public ProductDTO fetchProductDataById(@RequestParam String productId){
		return productService.findProductById(productId);
	}

	@PutMapping("/updateProduct")
	public Product updateProductData(@Valid @RequestBody ProductDTO product){
		Product responseDTO = productService.addProduct(product);
		return responseDTO;
	}

	@DeleteMapping("/delete")
	public String deleteProductDataById(@RequestParam String productId){
		return productService.deleteProduct(productId);
	}

}
