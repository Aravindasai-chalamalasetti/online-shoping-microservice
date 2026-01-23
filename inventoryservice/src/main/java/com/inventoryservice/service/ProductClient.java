package com.inventoryservice.service;

import com.inventoryservice.dto.ProductCodeDTO;
import com.inventoryservice.dto.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "product-service")
public interface ProductClient {
    @PostMapping("/product/fetchProductData")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO fetchProductData(@Valid @RequestBody ProductCodeDTO productCodeDTO);
}
