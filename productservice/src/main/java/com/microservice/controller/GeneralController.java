package com.microservice.controller;

import com.microservice.dto.RamDetailsDTO;
import com.microservice.dto.RamTypeDTO;
import com.microservice.dto.StorageTypeDTO;
import com.microservice.model.RamDetails;
import com.microservice.model.RamType;
import com.microservice.model.StorageType;
import com.microservice.service.GeneralService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/general")
public class GeneralController {

    public GeneralController(GeneralService service) {
        this.service = service;
    }
    private final GeneralService service;

    @PostMapping("/ramdetails/add")
    @ResponseStatus(HttpStatus.CREATED)
    public RamDetails insertRamDetails(@Valid @RequestBody RamDetailsDTO ramDetailsDTO){
        return service.saveRamDetails(ramDetailsDTO);
    }

    @GetMapping("/ramdetails/list")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<RamDetailsDTO> fetchRamDetailsList(){
        return service.fetchRamDetailsList();
    }

    @PostMapping("/ramtype/add")
    @ResponseStatus(HttpStatus.CREATED)
    public RamType insertRamTypeInfo(@Valid @RequestBody RamTypeDTO ramTypeDTO){
        return service.saveRamTypeInfo(ramTypeDTO);
    }

    @GetMapping("/ramtype/list")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<RamTypeDTO> fetchRamTypeList(){
        return service.fetchRamTypeList();
    }

    @PostMapping("/storagetype/add")
    @ResponseStatus(HttpStatus.CREATED)
    public StorageType insertStorageType(@Valid @RequestBody StorageTypeDTO storageTypeDTO){
        return service.saveStorageType(storageTypeDTO);
    }

    @GetMapping("/storagetype/list")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<StorageTypeDTO> fetchStorageTypeList(){
        return service.fetchStorageTypeList();
    }
}
