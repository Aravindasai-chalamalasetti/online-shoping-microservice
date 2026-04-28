package com.inventoryservice.serviceDTO;

import com.inventoryservice.dto.InventoryCodeDTO;
import com.inventoryservice.dto.InventoryDTO;
import com.inventoryservice.dto.InventoryDetailsDTO;
import com.inventoryservice.dto.ProductCodeDTO;
import com.inventoryservice.model.Inventory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryServiceDto {

	public Inventory convertDtoToInvntory(InventoryDTO dto) {
		Inventory inv = new Inventory();
		inv.setInventoryCode(dto.getInventoryCode());
		inv.setInventoryQuantity(dto.getInventoryQuantity());
		inv.setProductId(dto.getProductId());
		inv.setStorage(dto.getStorage());
		inv.setInventoryId(dto.getInventoryId());
		inv.setRamSize(dto.getRamSize());
		inv.setSingleUnitPrice(dto.getSingleUnitPrice());
		return inv;
	}

	public InventoryDTO convertInventoryToDto(Inventory inv) {
		InventoryDTO inventory = new InventoryDTO.InventoryDTOBuilder()
				.setInventoryId(inv.getInventoryId())
				.setInventoryCode(inv.getInventoryCode())
				.setProductId(inv.getProductId())
				.setStorage(inv.getStorage())
				.setInventoryQuantity(inv.getInventoryQuantity())
				.setRamSize(inv.getRamSize())
				.setSingleUnitPrice(inv.getSingleUnitPrice())
				.build();
		return inventory;
	}

	public List<InventoryDTO> convertInventoryToDtoList(List<Inventory> inv){
		List<InventoryDTO> dto = inv.stream().map(r->mapInventoryToDto(r)).toList();
		return dto;
	}

	private InventoryDTO mapInventoryToDto(Inventory inv) {
		InventoryDTO inventory = new InventoryDTO.InventoryDTOBuilder()
				.setInventoryId(inv.getInventoryId())
				.setInventoryCode(inv.getInventoryCode())
				.setProductId(inv.getProductId())
				.setStorage(inv.getStorage())
				.setInventoryQuantity(inv.getInventoryQuantity())
				.setRamSize(inv.getRamSize())
				.setSingleUnitPrice(inv.getSingleUnitPrice())
				.build();
		return inventory;
	}

	public List<InventoryCodeDTO> convertInventoryToDTO(List<Inventory> invCode){
		List<InventoryCodeDTO> inv = invCode.stream().map(r->mapInvListToDto(r)).collect(Collectors.toList());
		return inv;
	}

	private InventoryCodeDTO mapInvListToDto(Inventory inv){
		InventoryCodeDTO invoice = new InventoryCodeDTO.InventoryCodeDTOBuilder()
				.setInventoryCode(inv.getInventoryCode())
				.setInStock(inv.getInventoryQuantity() > 0)
				.setInventoryId(inv.getInventoryId())
				.setStorage(inv.getStorage())
				.setRamSize(inv.getRamSize())
				.setSingleUnitPrice(inv.getSingleUnitPrice())
				.build();
		return invoice;
	}

	public ProductCodeDTO mapInventoryToProductCodeDto(String productName,String storage,String ramSize){
		ProductCodeDTO p = new ProductCodeDTO();
		p.setProductName(productName);
		p.setStorageCapacity(storage);
		p.setRamSize(ramSize);
		return p;
	}

	public List<InventoryDetailsDTO> convertInventoryToDetailsDtoList(List<Inventory> inv){
		List<InventoryDetailsDTO> list = inv.stream().map((u)->mapInvToDtoList(u)).collect(Collectors.toList());
		return  list;
	}

	private InventoryDetailsDTO mapInvToDtoList(Inventory u) {
		InventoryDetailsDTO detailsDTO = new InventoryDetailsDTO.InventoryDetailsDTOBuilder()
				.setInventoryId(u.getInventoryId())
				.setInventoryCode(u.getInventoryCode())
				.setInventoryQuantity(u.getInventoryQuantity())
				.setProductId(u.getProductId())
				.setStorage(u.getStorage())
				.setSingleUnitPrice(u.getSingleUnitPrice())
				.setRamSize(u.getRamSize())
				.build();
		return  detailsDTO;
	}
}
