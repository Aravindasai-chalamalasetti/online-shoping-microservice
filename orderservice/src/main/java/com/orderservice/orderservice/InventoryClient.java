package com.orderservice.orderservice;

import com.orderservice.dto.InventoryCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="inventory-service")
public interface InventoryClient {
    @GetMapping("/inventory/fetchInStockDetails")
    List<InventoryCodeDTO> isInStock(@RequestParam("skucode") List<String> invCode);
}
