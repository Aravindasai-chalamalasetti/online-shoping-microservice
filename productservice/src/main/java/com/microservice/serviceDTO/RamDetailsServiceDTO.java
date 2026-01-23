package com.microservice.serviceDTO;

import com.microservice.dto.RamDetailsDTO;
import com.microservice.model.RamDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RamDetailsServiceDTO {

    public RamDetails convertDtoToRamDetails(RamDetailsDTO ramDetailsDTO){
        RamDetails ramDetails = new RamDetails();
        ramDetails.setRamId(ramDetailsDTO.getRamId());
        ramDetails.setRamSize(ramDetailsDTO.getRamSize());
        return ramDetails;
    }

    public RamDetailsDTO convertRamDetailsToDto(RamDetails ramDetails){
        RamDetailsDTO ramDetailsDTO = new RamDetailsDTO.RamDetailsDTOBuilder()
                .setRamId(ramDetails.getRamId())
                .setRamSize(ramDetails.getRamSize()).build();
        return ramDetailsDTO;
    }

    public List<RamDetailsDTO> convertRamDetailsToDtoList(List<RamDetails> ramDetails){
        List<RamDetailsDTO> ramDetailsDTOList = ramDetails.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return ramDetailsDTOList;
    }

    private RamDetailsDTO mapToDto(RamDetails r) {
        RamDetailsDTO ramDetailsDTO = convertRamDetailsToDto(r);
        return ramDetailsDTO;
    }
}
