package com.orderservice.database;

import com.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDatabase extends JpaRepository<Order, Long>{
Order findByOrderNumber(String orderNumber);

Order findByUserId(String userId);
}
