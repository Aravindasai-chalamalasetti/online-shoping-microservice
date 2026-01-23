package com.orderservice.controller;

import com.orderservice.dto.GeneralHttpResponseDTO;
import com.orderservice.dto.OrderDTO;
import com.orderservice.orderservice.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
		name = "CRUD REST APIs for Order",
		description = "CRUD REST APIs in Order to CREATE,FETCH,UPDATE and DELETE order details"
)
@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

	@Operation(
			summary = "Create Order REST API",
			description = "REST API to create new Order"
	)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP status created"
	)
    @PostMapping("/addProduct")
	public GeneralHttpResponseDTO<OrderDTO> addProduct(@Valid @RequestBody OrderDTO dto) {
		return orderService.addProduct(dto);
	}

	@Operation(
			summary = "Fetch Order REST API",
			description = "REST API to fetch Order details list"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status Ok"
	)
	@GetMapping("/fetchAllOrdersList")
	public GeneralHttpResponseDTO<List<OrderDTO>> fetchAllOrders(){
		return orderService.fetchOrderList();
	}

	@GetMapping("/fetchData")
	public GeneralHttpResponseDTO<OrderDTO> fetchInventoryDataById(@RequestParam Long orderId){
		return orderService.fetchOrderDataById(orderId);
	}

	@PutMapping("/updateOrder")
	public GeneralHttpResponseDTO<OrderDTO> updateInventoryData(@Valid @RequestBody OrderDTO order){
		GeneralHttpResponseDTO<OrderDTO> responseDTO = orderService.addProduct(order);
		return responseDTO;
	}

	@DeleteMapping("/delete")
	public ResponseEntity<GeneralHttpResponseDTO<OrderDTO>> deleteInventoryDataById(@RequestParam Long orderId){
		return new ResponseEntity<>(orderService.deleteOrderData(orderId), HttpStatus.OK);
	}
}
