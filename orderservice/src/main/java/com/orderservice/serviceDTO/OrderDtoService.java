package com.orderservice.serviceDTO;

import com.orderservice.dto.*;
import com.orderservice.model.Order;
import com.orderservice.model.OrderLineItems;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDtoService {

	public Order convertDtoToOrder(OrderDTO dto) {
		Order order =  new Order();
		List<OrderLineItems> item = dto.getOrderItems().stream().map(r->mapOrderToDto(r)).collect(Collectors.toList());
		order.setOrderItems(item);
		order.setOrderNumber(UUID.randomUUID().toString());
		order.setUserId(dto.getUserId());
		return order;
	}
	
	private OrderLineItems mapOrderToDto(OrderLineItemsDto dto) {
		OrderLineItems order = new OrderLineItems();
		order.setSkuCode(dto.getSkuCode());
		order.setItemId(dto.getItemId());
		order.setItemPrice(dto.getItemPrice());
		order.setItemQuantity(dto.getItemQuantity());
		order.setStorage(dto.getStorage());
		return order;
	}

	public Order convertProductDtoToOrder(Order dto,List<InventoryCodeDTO> inventoryList) {
		Order order = new Order();
		List<OrderLineItems> finalItems = dto.getOrderItems().stream()
				.map(orderItem ->
						inventoryList.stream()
								.filter(inv -> orderItem.getSkuCode().equals(inv.getInventoryCode())
										&& orderItem.getStorage().equals(inv.getStorage())
										&& inv.isInStock())
								.findFirst()
								.map(inv -> mapOrderToProductDto(orderItem, inv))
								.orElse(null)
				)
				.filter(item -> item != null)
				.collect(Collectors.toList());

		order.setOrderItems(finalItems);
		order.setOrderNumber(dto.getOrderNumber() != null ? dto.getOrderNumber() : UUID.randomUUID().toString());
		order.setUserId(dto.getUserId());
		return order;
	}

	private OrderLineItems mapOrderToProductDto(OrderLineItems dto,InventoryCodeDTO inv) {

		if(dto != null && inv != null && dto.getSkuCode().equals(inv.getInventoryCode()) && dto.getStorage().equals(inv.getStorage()) && inv.isInStock() == true )  {
			OrderLineItems order  = new OrderLineItems();
			order.setSkuCode(dto.getSkuCode());
			order.setItemId(dto.getItemId());
			order.setItemPrice(inv.getSingleUnitPrice().multiply(BigDecimal.valueOf(dto.getItemQuantity())));
			order.setItemQuantity(dto.getItemQuantity());
			order.setStorage(dto.getStorage());
			order.setInventoryId(inv.getInventoryId());
			return order;
		}
		return null;
	}
	
	public List<OrderDTO> convertOrderToDtoList(List<Order> order){
		List<OrderDTO> dto = order.stream().map(r-> mapOrderToDto(r)).collect(Collectors.toList());
		return dto;
	}
	
	public OrderDTO mapOrderToDto(Order order){
		OrderDTO dto = new OrderDTO();
				dto.setOrderId(order.getOrderId());
				dto.setOrderNumber(order.getOrderNumber());
				dto.setUserId(order.getUserId());
				dto.setOrderItems(convertOrderItemsToDtoList(order.getOrderItems()));
		return dto;
				
	}
	
	public List<OrderLineItemsDto> convertOrderItemsToDtoList(List<OrderLineItems> order){
		List<OrderLineItemsDto> items = order.stream().map(r->mapLineItemsToItemsDto(r)).collect(Collectors.toList());
		return items;
	}
	
	private OrderLineItemsDto mapLineItemsToItemsDto(OrderLineItems dto) {
		OrderLineItemsDto order = new OrderLineItemsDto.OrderLineItemsDtoBuilder()
				.setItemId(dto.getItemId())
				.setSkuCode(dto.getSkuCode())
				.setItemPrice(dto.getItemPrice())
				.setItemQuantity(dto.getItemQuantity())
				.setStorage(dto.getStorage())
				.build();
		return order;
	}

}
