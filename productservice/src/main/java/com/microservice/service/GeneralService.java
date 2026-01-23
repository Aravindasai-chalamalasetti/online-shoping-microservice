package com.microservice.service;

import com.microservice.database.RamDetailsRepository;
import com.microservice.database.RamTypeRepository;
import com.microservice.database.StorageTypeRepository;
import com.microservice.dto.RamDetailsDTO;
import com.microservice.dto.RamTypeDTO;
import com.microservice.dto.StorageTypeDTO;
import com.microservice.model.RamDetails;
import com.microservice.model.RamType;
import com.microservice.model.StorageType;
import com.microservice.sequence.SequenceGeneratorService;
import com.microservice.serviceDTO.RamDetailsServiceDTO;
import com.microservice.serviceDTO.RamTypeServiceDTO;
import com.microservice.serviceDTO.StorageTypeServiceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralService {

    public GeneralService(RamDetailsRepository ramDetailsRepository, RamTypeRepository ramTypeRepository, StorageTypeRepository storageTypeRepository, RamDetailsServiceDTO ramDetailsServiceDTO, RamTypeServiceDTO ramTypeServiceDTO, StorageTypeServiceDTO storageTypeServiceDTO, SequenceGeneratorService sequenceGeneratorService) {
        this.ramDetailsRepository = ramDetailsRepository;
        this.ramTypeRepository = ramTypeRepository;
        this.storageTypeRepository = storageTypeRepository;
        this.ramDetailsServiceDTO = ramDetailsServiceDTO;
        this.ramTypeServiceDTO = ramTypeServiceDTO;
        this.storageTypeServiceDTO = storageTypeServiceDTO;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }
    private final RamDetailsRepository ramDetailsRepository;

    private final RamTypeRepository ramTypeRepository;

    private final StorageTypeRepository storageTypeRepository;

    private final RamDetailsServiceDTO ramDetailsServiceDTO;

    private final RamTypeServiceDTO ramTypeServiceDTO;

    private final StorageTypeServiceDTO storageTypeServiceDTO;

    private final SequenceGeneratorService sequenceGeneratorService;

    public RamDetails saveRamDetails(RamDetailsDTO ramDetailsDTO){
        RamDetails ramDetails = null;
        if(ramDetailsDTO.getRamSize() != null){
            RamDetails ramDetailsDTO1 = ramDetailsServiceDTO.convertDtoToRamDetails(ramDetailsDTO);
            ramDetails = new RamDetails();
            ramDetailsDTO1.generateIdIfNeeded(sequenceGeneratorService);
            ramDetails = ramDetailsRepository.save(ramDetailsDTO1);
        }
        return ramDetails;
    }

    public List<RamDetailsDTO> fetchRamDetailsList(){
        List<RamDetails> list = ramDetailsRepository.findAll();
        return ramDetailsServiceDTO.convertRamDetailsToDtoList(list);
    }

    public RamDetailsDTO findByRamDetailsId(Long id){
        RamDetailsDTO ramDetails = null;
        if(id != null){
            RamDetails ramDetails1 = ramDetailsRepository.findByRamId(id);
            if(ramDetails1 != null){
                ramDetails = new RamDetailsDTO();
                ramDetails = ramDetailsServiceDTO.convertRamDetailsToDto(ramDetails1);
            }
        }
        return ramDetails;
    }

    public RamType saveRamTypeInfo(RamTypeDTO ramTypeDTO){
        RamType ramType = null;
        if(ramTypeDTO.getRamType() != null){
            RamType ramType1 =  ramTypeServiceDTO.convertDtoToRamType(ramTypeDTO);
            ramType = new RamType();
            ramType1.generateIdIfNeeded(sequenceGeneratorService);
            ramType = ramTypeRepository.save(ramType1);
        }return ramType;
    }

    public List<RamTypeDTO> fetchRamTypeList(){
        List<RamType> ramTypes = ramTypeRepository.findAll();
        return ramTypeServiceDTO.convertRamTypeToDtoList(ramTypes);
    }

    public RamTypeDTO findByRamTypeId(Long id){
        RamTypeDTO ramTypeDTO = null;
        if(id != null){
            RamType ramType = ramTypeRepository.findByRamTypeId(id);
            if(ramType != null){
                ramTypeDTO = new RamTypeDTO();
                ramTypeDTO = ramTypeServiceDTO.convertRamTypeToDto(ramType);
            }
        }return ramTypeDTO;
    }

    public StorageType saveStorageType(StorageTypeDTO storageTypeDTO){
        StorageType storageType = null;
        if(storageTypeDTO.getStorageType() != null){
            StorageType storageType1 = storageTypeServiceDTO.convertDtoToStorageType(storageTypeDTO);
            storageType = new StorageType();
            storageType1.generateIdIfNeeded(sequenceGeneratorService);
            storageType = storageTypeRepository.save(storageType1);
        }return storageType;
    }

    public List<StorageTypeDTO> fetchStorageTypeList(){
        List<StorageType> storageTypes = storageTypeRepository.findAll();
        return storageTypeServiceDTO.convertStorageTypeToDtoList(storageTypes);
    }

    public StorageTypeDTO findByStorageTypeId(Long id){
        StorageTypeDTO storageTypeDTO = null;
        if(id != null){
            StorageType storageType = storageTypeRepository.findByStorageTypeId(id);
            if(storageType != null){
                storageTypeDTO = new StorageTypeDTO();
                storageTypeDTO = storageTypeServiceDTO.convertStorageTypeToDto(storageType);
            }
        }return storageTypeDTO;
    }
}
