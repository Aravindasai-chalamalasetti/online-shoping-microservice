package com.inventoryservice.database;

import com.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryDatabase extends JpaRepository<Inventory, Long>{
	Optional<Inventory> findByInventoryCode(String inventoryCode);

	List<Inventory> findByInventoryCodeIn(List<String> inventoryCode);

	Inventory findByInventoryId(Long inventoryId);

	Inventory findByInventoryCodeAndStorage(String inventoryCode,String storage);
}
