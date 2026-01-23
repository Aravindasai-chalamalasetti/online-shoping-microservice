package com.microservice.serviceDTO;

import com.microservice.dto.StorageTypeDTO;
import com.microservice.model.StorageType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StorageTypeServiceDTO {
    public StorageType convertDtoToStorageType(StorageTypeDTO storageTypeDTO){
        StorageType storageType = new StorageType();
        storageType.setStorageTypeId(storageTypeDTO.getStorageTypeId());
        storageType.setStorageType(storageTypeDTO.getStorageType());
        return storageType;
    }

    public StorageTypeDTO convertStorageTypeToDto(StorageType storageType){
        StorageTypeDTO storageTypeDTO = new StorageTypeDTO.StorageTypeDTOBuilder()
                .setStorageTypeId(storageType.getStorageTypeId())
                .setStorageType(storageType.getStorageType()).build();
        return storageTypeDTO;
    }

    public List<StorageTypeDTO> convertStorageTypeToDtoList(List<StorageType> storageTypes){
        List<StorageTypeDTO> storageTypeDTOS = storageTypes.stream().map(r->mapStorageTypeToDto(r)).collect(Collectors.toList());
        return storageTypeDTOS;
    }

    private StorageTypeDTO mapStorageTypeToDto(StorageType r) {
        StorageTypeDTO storageTypeDTO = convertStorageTypeToDto(r);
        return  storageTypeDTO;
    }
}
