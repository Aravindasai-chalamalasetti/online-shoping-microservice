package com.user.dto;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class OrderDTO {
	private Long orderId;
	private String orderNumber;
	private String userId;
	@NotEmpty(message = "Order items cannot be empty")
    private List<OrderLineItemsDto> orderItems;
	public OrderDTO(){}

	public OrderDTO(Long orderId, String orderNumber, String userId, List<OrderLineItemsDto> orderItems) {
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.userId = userId;
		this.orderItems = orderItems;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OrderLineItemsDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderLineItemsDto> orderItems) {
		this.orderItems = orderItems;
	}
}
