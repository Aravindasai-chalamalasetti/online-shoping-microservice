package com.inventoryservice.service;

import com.inventoryservice.database.InventoryDatabase;
import com.inventoryservice.dto.*;
import com.inventoryservice.exception.ResourceNotFoundException;
import com.inventoryservice.model.Inventory;
import com.inventoryservice.serviceDTO.InventoryServiceDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

	public InventoryService(InventoryDatabase repo, ProductClient productClient, InventoryServiceDto mapper) {
		this.repo = repo;
        this.productClient = productClient;
        this.mapper = mapper;
	}

	private final InventoryDatabase repo;

	private final ProductClient productClient;

	private final InventoryServiceDto mapper;

	public GeneralHttpResponseDTO<InventoryDTO> addInventory(InventoryDTO inv) {
		ProductCodeDTO productCodeDTO = mapper.mapInventoryToProductCodeDto(inv.getInventoryCode(),inv.getStorage());
		ProductDTO productClient1 = productClient.fetchProductData(productCodeDTO);
		Inventory stockExist = repo.findByInventoryCodeAndStorage(inv.getInventoryCode(),inv.getStorage());
		Inventory saveInventory = null;
		Inventory inventory = null;
		GeneralHttpResponseDTO<InventoryDTO> responseDTO = new GeneralHttpResponseDTO<>();
		if (productClient1 != null && productClient1.getProductId() != null){
		if(stockExist == null){
			inventory = mapper.convertDtoToInvntory(inv);
			inventory.setProductId(productClient1.getProductId());
			inventory.setSingleUnitPrice(productClient1.getPrice());
			saveInventory = repo.save(inventory);
			responseDTO.setResponseCode(201);
			responseDTO.setResponseMessage("Successfully save inventory data");
		} else if (stockExist != null && stockExist.getInventoryId() != null) {
			inventory = mapper.convertDtoToInvntory(inv);
			inventory.setInventoryId(stockExist.getInventoryId());
			saveInventory = repo.save(inventory);
			responseDTO.setResponseCode(200);
			responseDTO.setResponseMessage("Successfully updated inventory data");
		}
		}else {
			responseDTO.setResponseCode(401);
			responseDTO.setResponseMessage("This product "+inv.getInventoryCode()+" is not exist");
		}
		if (saveInventory != null) {
			responseDTO.setResponseBody(mapper.convertInventoryToDto(saveInventory));
		}
		return responseDTO;
	}

	public List<InventoryDTO> fetchInventoriesList(){
		List<Inventory> invList = repo.findAll();
		return mapper.convertInventoryToDtoList(invList);
	}

	public List<InventoryDetailsDTO> fetchInventoryDetailsList(){
		List<Inventory> invList = repo.findAll();
		return mapper.convertInventoryToDetailsDtoList(invList);
	}

	public GeneralHttpResponseDTO<InventoryDTO> fetchInventoryDataById(Long inventoryId){
		GeneralHttpResponseDTO<InventoryDTO> responseDTO = new GeneralHttpResponseDTO<>();
		Inventory inventory = repo.findById(inventoryId).orElseThrow(
				()->new ResourceNotFoundException("This " + inventoryId + " Id is not exist in database")
		);
		responseDTO.setResponseCode(200);
		responseDTO.setResponseBody(mapper.convertInventoryToDto(inventory));
		return responseDTO;
	}

	@Transactional(readOnly = true)
	public List<InventoryCodeDTO> inInStock(List<String> invCode) {
		List<Inventory> invList = repo.findByInventoryCodeIn(invCode);
		List<InventoryCodeDTO> inv = mapper.convertInventoryToDTO(invList);
		System.out.println("inv--service--output--- "+ inv);
		return invList.stream().map(invv -> new InventoryCodeDTO.InventoryCodeDTOBuilder()
				.setInventoryCode(invv.getInventoryCode())
				.setInStock(invv.getInventoryQuantity() > 0)
				.setInventoryId(invv.getInventoryId())
				.setStorage(invv.getStorage())
				.setSingleUnitPrice(invv.getSingleUnitPrice())
				.build()).collect(Collectors.toList());

	}

	public GeneralHttpResponseDTO<InventoryDTO> deleteInventoryData(Long inventoryId){
		GeneralHttpResponseDTO<InventoryDTO> responseDTO = new GeneralHttpResponseDTO<>();
		Inventory inventory = repo.findByInventoryId(inventoryId);
		if(inventory != null && inventory.getInventoryId() != null){
			repo.delete(inventory);
			responseDTO.setResponseCode(200);
			responseDTO.setResponseMessage("Successfully deleted inventory data by id : "+inventoryId);
		}else{
			responseDTO.setResponseCode(403);
			responseDTO.setResponseMessage("Unable to deleted inventory data because id : "+inventoryId + " is not exist");
		}return responseDTO;
	}
}
