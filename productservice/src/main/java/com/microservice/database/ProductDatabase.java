package com.microservice.database;

import com.microservice.model.Product;
import com.microservice.model.RamDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDatabase extends MongoRepository<Product,String>{

    public Product findByProductNameAndStorageCapacity(String productName,String storageCapacity);

    @Query("""
    {
      'productName': ?0,
      'storageCapacity': ?1
    }
    """)
    public List<Product> existProductNameAndStorageCapacity(String productName, String storageCapacity);

    public Product findByProductNameAndStorageCapacityAndRamDetails(String productName, String storageCapacity, RamDetails ramDetails);
}
