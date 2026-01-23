package com.microservice.serviceDTO;

import com.microservice.dto.RamTypeDTO;
import com.microservice.model.RamType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RamTypeServiceDTO {

    public RamType convertDtoToRamType(RamTypeDTO ramTypeDTO){
        RamType ramType = new RamType();
        ramType.setRamTypeId(ramTypeDTO.getRamTypeId());
        ramType.setRamType(ramTypeDTO.getRamType());
        return ramType;
    }

    public RamTypeDTO convertRamTypeToDto(RamType ramType){
        RamTypeDTO ramTypeDTO = new RamTypeDTO.RamTypeDTOBuilder()
                .setRamTypeId(ramType.getRamTypeId())
                .setRamType(ramType.getRamType()).build();
        return ramTypeDTO;
    }

    public List<RamTypeDTO> convertRamTypeToDtoList(List<RamType> ramTypes){
        List<RamTypeDTO> ramTypeDTOS = ramTypes.stream().map(r->convertModelToDto(r)).collect(Collectors.toList());
        return ramTypeDTOS;
    }

    private RamTypeDTO convertModelToDto(RamType r) {
        RamTypeDTO ramTypeDTO = convertRamTypeToDto(r);
        return ramTypeDTO;
    }
}
