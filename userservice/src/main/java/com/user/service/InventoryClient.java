package com.user.service;

import com.user.dto.InventoryDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryClient {
    @GetMapping("/inventory/fetchInventoryDetailsList")
    public ResponseEntity<List<InventoryDetailsDTO>> fetchInventoryList();
}
