package com.microservice.database;

import com.microservice.model.RamType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamTypeRepository extends MongoRepository<RamType,Long>
{
    public RamType findByRamTypeId(Long ramTypeId);
}
