package com.microservice.database;

import com.microservice.model.RamDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamDetailsRepository extends MongoRepository<RamDetails,Long> {
    public RamDetails findByRamId(Long ramId);

    public RamDetails findByRamSize(String ramSize);
}
