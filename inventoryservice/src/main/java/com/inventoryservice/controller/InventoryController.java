package com.inventoryservice.controller;

import com.inventoryservice.dto.*;
import com.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//api documentation url : http://localhost:8082/swagger-ui/index.html(8082 is inventoryService port)
@Tag(
		name = "CRUD REST APIs for Inventory",
		description = "CRUD REST APIs in Inventory to CREATE,FETCH,UPDATE and DELETE inventory details"
)
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

	private final InventoryService inventoryService;

	@Operation(
			summary = "Create Inventory REST API",
			description = "REST API to create new Inventory"
	)
	@ApiResponse(
			responseCode = "201",
			description = "HTTP status created"

	)
    @PostMapping("/addInventory")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<GeneralHttpResponseDTO<InventoryDTO>> addInventoryProduct(@Valid @RequestBody InventoryDTO inv) {
		GeneralHttpResponseDTO<InventoryDTO> responseDTO = inventoryService.addInventory(inv);
		if(responseDTO.getResponseCode() == 201 || responseDTO.getResponseCode() == 200){
			return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(
			summary = "Fetch Inventory REST API",
			description = "REST API to fetch Inventory details list"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status Ok"
	)
	@GetMapping("/fetchInventoryList")
	public ResponseEntity<List<InventoryDTO>> fetchInventoryList(){
		return new ResponseEntity<>(inventoryService.fetchInventoriesList(),HttpStatus.OK);
	}

	@GetMapping("/fetchInventoryDetailsList")
	public ResponseEntity<List<InventoryDetailsDTO>> fetchInventoryDetailsList(){
		return new ResponseEntity<>(inventoryService.fetchInventoryDetailsList(),HttpStatus.OK);
	}

	@Operation(
			summary = "Fetch Inventory REST API",
			description = "REST API to fetch Inventory details based on productName"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status Ok"
	)
	@GetMapping("/fetchInStockDetails")
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryCodeDTO> isInStock(@RequestParam("skucode") List<String> invCode) {
		System.out.println("Received input sku-code: " + invCode);
		List<InventoryCodeDTO> inv = inventoryService.inInStock(invCode);
		System.out.println("Received output sku-code: " +  inv.get(0).getInventoryCode() + "----" + inv.get(0).getIsInStock());
		return inv;
	}

	@Operation(
			summary = "Fetch Inventory REST API",
			description = "REST API to fetch Inventory data by id"
	)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status Ok"
	)
	@GetMapping("/fetchData")
	public ResponseEntity<GeneralHttpResponseDTO<InventoryDTO>> fetchInventoryDataById(@RequestParam Long inventoryId){
		return new ResponseEntity<>(inventoryService.fetchInventoryDataById(inventoryId),HttpStatus.OK);
	}

	@Operation(
			summary = "Update Inventory REST API",
			description = "REST API to Update Inventory data"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP status Ok"
			),
			@ApiResponse(
					description = "Show sample Product information",
					content = @Content(
							schema = @Schema(implementation = ProductDTO.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "HTTP status BAD REQUEST"
			)
	})
	@PutMapping("/updateInventory")
	public ResponseEntity<GeneralHttpResponseDTO<InventoryDTO>> updateInventoryData(@Valid @RequestBody InventoryDTO inv){
		GeneralHttpResponseDTO<InventoryDTO> responseDTO = inventoryService.addInventory(inv);
		if(responseDTO.getResponseCode() == 200 || responseDTO.getResponseCode() == 201){
			return new ResponseEntity<>(responseDTO,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(
			summary = "Delete Inventory REST API",
			description = "REST API to Delete Inventory data"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "HTTP status Ok"
			),
			@ApiResponse(
					responseCode = "400",
					description = "HTTP status BAD REQUEST"
			)
	})
	@DeleteMapping("/delete")
	public ResponseEntity<GeneralHttpResponseDTO<InventoryDTO>> deleteInventoryDataById(@RequestParam Long inventoryId){
		GeneralHttpResponseDTO<InventoryDTO> responseDTO = inventoryService.deleteInventoryData(inventoryId);
		if(responseDTO.getResponseCode() == 200) {
			return new ResponseEntity<>(responseDTO,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
		}
	}
}
