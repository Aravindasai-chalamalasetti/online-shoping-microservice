package com.user.converter;

import com.user.dto.CustomInventoryDTO;
import com.user.dto.InventoryDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryMapper {

    public List<CustomInventoryDTO> convertInventoryToDtoCustomList(List<InventoryDetailsDTO> inv){
        List<CustomInventoryDTO> inventoryDTOList = inv.stream().map((u)->mapInventoryToDtoList(u)).collect(Collectors.toList());
        return inventoryDTOList;
    }

    private CustomInventoryDTO mapInventoryToDtoList(InventoryDetailsDTO u) {
        CustomInventoryDTO list = new CustomInventoryDTO.CustomInventoryDTOBuilder()
                .setInventoryCode(u.getInventoryCode())
                .setStorage(u.getStorage())
                .setRamSize(u.getRamSize())
                .setSingleUnitPrice(u.getSingleUnitPrice()).build();
        return list;
    }
}
