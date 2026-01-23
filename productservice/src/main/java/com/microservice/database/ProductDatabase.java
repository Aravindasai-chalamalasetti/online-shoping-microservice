package com.microservice.database;

import com.microservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDatabase extends MongoRepository<Product,String>{
    public Product findByProductNameAndStorageCapacity(String productName,String storageCapacity);
}
