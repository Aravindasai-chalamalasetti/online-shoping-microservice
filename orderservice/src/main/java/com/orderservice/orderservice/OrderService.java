package com.orderservice.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservice.database.OrderDatabase;
import com.orderservice.dto.*;
import com.orderservice.model.Order;
import com.orderservice.model.OrderLineItems;
import com.orderservice.serviceDTO.OrderDtoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderService {

	public OrderService(OrderDatabase repo, OrderDtoService dtoService, WebClient.Builder webClientBuilder, InventoryClient inventory, UserClient user){
        this.repo = repo;
        this.dtoService = dtoService;
        this.webClientBuilder = webClientBuilder;
        this.inventory = inventory;
        this.user = user;
    }
	private final OrderDatabase repo;

	private final OrderDtoService dtoService;

	private final WebClient.Builder webClientBuilder;

	private final InventoryClient inventory;

	private final UserClient user;

	public GeneralHttpResponseDTO<OrderDTO> addProduct(OrderDTO dto)  {
		GeneralHttpResponseDTO<OrderDTO> response = new GeneralHttpResponseDTO<>();
		Order order = null;
		try {
			order = dtoService.convertDtoToOrder(dto);
			List<OrderLineItems> orderItems = order.getOrderItems();
			List<String> orderItemCodes = orderItems.stream()
					.map(OrderLineItems::getSkuCode)
					.toList();

			//Testing to fetch data
			String inventoryData = webClientBuilder.build().get().uri("http://inventory-service/inventory/fetchInStockDetails",
								uriBuilder -> uriBuilder.queryParam("skucode", orderItemCodes.toArray()).build())
						.retrieve()
						.bodyToMono(String.class)
						.block();
				ObjectMapper objectMapper = new ObjectMapper();
				InventoryCodeDTO[] inventoryCodeDTO = objectMapper.readValue(inventoryData, InventoryCodeDTO[].class);

			if (orderItems == null || orderItems.isEmpty()) {
				response.setResponseMessage("No order items found");
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}

			List<InventoryCodeDTO> inventoryList = inventory.isInStock(orderItemCodes);

			if (inventoryList == null || inventoryList.isEmpty()) {
				response.setResponseMessage("Out of Stock (Inventory)");
				response.setStatus(HttpStatus.BAD_REQUEST);
				return response;
			}
				// MATCHED ITEMS (in stock + storage matches)
				List<OrderLineItems> matchedItems =
						orderItems.stream()
								.filter(orderItem ->
										inventoryList.stream().anyMatch(inv ->
												inv.getInventoryCode().equals(orderItem.getSkuCode()) &&
														inv.getStorage().equals(orderItem.getStorage()) && inv.getRamSize().equals(orderItem.getRamSize()) &&
														inv.isInStock() == true
										)).toList();

				// UNMATCHED ITEMS (missing in inventory)
				List<OrderLineItems> unmatchedItems =
						orderItems.stream()
								.filter(orderItem ->
										inventoryList.stream().noneMatch(inv ->
												inv.getInventoryCode().equals(orderItem.getSkuCode()) &&
														inv.getStorage().equals(orderItem.getStorage()) && inv.getRamSize().equals(orderItem.getRamSize())
										)).toList();

				// No matched items → FULL FAILURE
				if (matchedItems.isEmpty()) {
					List<String> errorList = unmatchedItems.stream()
							.map(item -> item.getSkuCode() + " , " + item.getRamSize() + " " +" (" + item.getStorage() + ") is not available in inventory")
							.toList();

					response.setResponseMessage(errorList.toString());
					response.setStatus(HttpStatus.NOT_FOUND);
					return response;
				}

				// Save only matched items in order
				order.setOrderItems(matchedItems);
			    order = dtoService.convertProductDtoToOrder(order,inventoryList);
				Order savedOrder = null;
				Order existingOrders = repo.findByUserId(order.getUserId());
				GeneralHttpResponseDTO<UserDTO> userId = user.findUuid(order.getUserId());
				boolean result = userId != null && userId.getResponseBody() != null && userId.getResponseBody().getUserId() != null;
				if(order != null && !order.getOrderItems().isEmpty() && result) {
					order.setUserId(userId.getResponseBody().getUuid());
					Order uniqueOrderNumber = repo.findByOrderNumber(order.getOrderNumber());
					Order orderNumber = null;
					if(existingOrders != null && existingOrders.getOrderId() != null && uniqueOrderNumber == null){
						orderNumber = existingOrders;
						order.setOrderId(existingOrders.getOrderId());
						order.setOrderNumber(existingOrders.getOrderNumber());
					}
					Long finalOrderId = existingOrders != null && existingOrders.getOrderId() != null
							? existingOrders.getOrderId()
							: order.getOrderId();
					if (order.getOrderId() == null && existingOrders == null && (orderNumber == null || !order.getOrderNumber().equals(orderNumber.getOrderNumber()))) {
							savedOrder = repo.save(order);
						} else if (order.getOrderId() != null && finalOrderId != null && existingOrders != null && orderNumber != null && order.getOrderNumber().equals(orderNumber.getOrderNumber())) {
							Long orderId = order.getOrderId() == null ? existingOrders.getOrderId() : order.getOrderId();
							Order order1 = repo.findById(orderId)
									.orElseThrow(() -> new ExceptionDTO(
											"Order id " + orderId + " is does not exist in the database",
											new Date(),
											HttpStatus.NOT_FOUND,
											null
									));
						List<OrderLineItems> mergedItems = new ArrayList<>(existingOrders.getOrderItems());
						for (OrderLineItems newItem : order.getOrderItems()) {
							Optional<OrderLineItems> duplicate = mergedItems.stream()
									.filter(e ->
											e.getSkuCode().equals(newItem.getSkuCode()) &&
													e.getStorage().equals(newItem.getStorage()) &&
													e.getRamSize().equals(newItem.getRamSize()))
									.findFirst();

							if (duplicate.isPresent()) {
								duplicate.get().setItemQuantity(
										duplicate.get().getItemQuantity() + newItem.getItemQuantity()
								);
							} else {
								mergedItems.add(newItem);
							}
						}

						order.setOrderItems(mergedItems);
						order.setOrderId(order1.getOrderId());
							if (order1.getOrderId().equals(order.getOrderId()) && order1.getOrderId() == order.getOrderId()) {
								savedOrder = repo.save(order);
							}
						}

				}else if(order.getUserId() == null || order.getUserId().isEmpty()){
					response.setResponseCode(401);
					response.setStatus(HttpStatus.NOT_FOUND);
					response.setDate(new Date());
					response.setResponseMessage("Failed to place order.");
				}

			if (savedOrder != null) {
				response.setResponseBody(dtoService.mapOrderToDto(savedOrder));
				response.setResponseCode(201);
				response.setStatus(HttpStatus.CREATED);
				response.setDate(new Date());
			} else {
				response.setResponseMessage("Failed to process order save/update.");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			}
				// Partial success → matched items placed, others missing
				if (!unmatchedItems.isEmpty()) {
					List<String> errors = unmatchedItems.stream()
							.map(item -> "Not available: " + item.getSkuCode() + "," + item.getRamSize() + " " +" (" + item.getStorage() + ")")
							.toList();

					response.setResponseMessage(errors.toString());
				} else {
					response.setResponseMessage("All items successfully placed");
				}

				return response;

			} catch (Exception e) {
				e.printStackTrace();
				response.setResponseMessage("Internal Server Error");
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				return response;
			}
	}

	public GeneralHttpResponseDTO<List<OrderDTO>> fetchOrderList(){
		GeneralHttpResponseDTO<List<OrderDTO>> ord = new GeneralHttpResponseDTO<>();
		List<Order> orderList = repo.findAll();
		if(orderList != null && !orderList.isEmpty()){
			ord.setDate(new Date());
			ord.setResponseCode(201);
			ord.setResponseBody(dtoService.convertOrderToDtoList(orderList));
			ord.setStatus(HttpStatus.ACCEPTED);
		}else{
			ord.setDate(new Date());
			ord.setResponseCode(401);
			ord.setResponseMessage("Order list is empty");
			ord.setStatus(HttpStatus.BAD_REQUEST);
		}
		return ord;
	}

	public GeneralHttpResponseDTO<OrderDTO> fetchOrderDataById(Long orderId){
		GeneralHttpResponseDTO<OrderDTO> ord = new GeneralHttpResponseDTO<>();
		Order order = repo.findById(orderId).orElseThrow(
				()->new ExceptionDTO("Order id "+ orderId+" is not exist in database",new Date(),HttpStatus.NOT_FOUND,null)
		);
		ord.setResponseCode(200);
		ord.setResponseBody(dtoService.mapOrderToDto(order));
		return ord;
	}

	public GeneralHttpResponseDTO<OrderDTO> deleteOrderData(Long orderId){
		GeneralHttpResponseDTO<OrderDTO> responseDTO = fetchOrderDataById(orderId);
		if(responseDTO.getResponseBody() != null){
			repo.deleteById(orderId);
			responseDTO.setResponseCode(200);
			responseDTO.setResponseMessage("Successfully order will be deleted");
		}else{
			responseDTO.setDate(new Date());
			responseDTO.setResponseCode(404);
			responseDTO.setResponseMessage("Unable to delete Order because "+ orderId+" is not exist in database");
		}
		return responseDTO;
	}
}
