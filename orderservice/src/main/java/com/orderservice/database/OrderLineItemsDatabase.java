package com.orderservice.database;

import com.orderservice.model.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineItemsDatabase extends JpaRepository<OrderLineItems, Long>{

}
