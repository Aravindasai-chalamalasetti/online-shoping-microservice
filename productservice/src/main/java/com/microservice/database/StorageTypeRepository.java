package com.microservice.database;

import com.microservice.model.StorageType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageTypeRepository extends MongoRepository<StorageType,Long> {
    public StorageType findByStorageTypeId(Long storageTypeId);
}
