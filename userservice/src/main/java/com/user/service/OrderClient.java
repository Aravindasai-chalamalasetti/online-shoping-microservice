package com.user.service;

import com.user.dto.GeneralHttpResponseDTO;
import com.user.dto.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderClient {
    @PostMapping("/order/addProduct")
    public GeneralHttpResponseDTO<OrderDTO> addProduct(@Valid @RequestBody OrderDTO dto);

    @GetMapping("/order/fetchAllOrdersList")
    public GeneralHttpResponseDTO<List<OrderDTO>> fetchAllOrders();
}
