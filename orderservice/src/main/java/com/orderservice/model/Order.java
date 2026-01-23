package com.orderservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name="ordertable")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
	private String orderNumber;
	@NotEmpty(message = "User Id cannot be empty")
	private String userId;
	@NotEmpty(message = "Order items cannot be empty")
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderLineItems> orderItems;

	public Order(Long orderId, String orderNumber,String userId, List<OrderLineItems> orderItems) {
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.userId = userId;
		this.orderItems = orderItems;
	}

	public Order(){

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

	public List<OrderLineItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderLineItems> orderItems) {
		this.orderItems = orderItems;
	}
}
