package com.user.service;

import com.user.converter.InventoryMapper;
import com.user.converter.UserMapper;
import com.user.dto.*;
import com.user.model.Role;
import com.user.model.User;
import com.user.repository.RoleRepository;
import com.user.repository.UserRepository;

import com.user.utils.JWTTokenProvider;
import com.user.utils.SecurityUtil;
import com.user.utils.Utilities;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

private final UserRepository repo;

private final RoleRepository roleRepository;

private final UserMapper mapper;

private final JWTTokenProvider tokenProvider;

private final Utilities utilities;

private final InventoryClient inventory;

private final InventoryMapper inventoryMapper;

private final SecurityUtil securityUtil;

private final OrderClient order;

    public UserService(UserRepository repo, RoleRepository roleRepository, UserMapper mapper, JWTTokenProvider tokenProvider, Utilities utilities, InventoryClient inventory, InventoryMapper inventoryMapper, SecurityUtil securityUtil, OrderClient order) {
        this.repo = repo;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.tokenProvider = tokenProvider;
        this.utilities = utilities;
        this.inventory = inventory;
        this.inventoryMapper = inventoryMapper;
        this.securityUtil = securityUtil;
        this.order = order;
    }

    public GeneralHttpResponseDTO<UserDTO> saveUserData(UserDTO userDTO){
        User user = mapper.convertDtoToUser(userDTO);
        Set<Role> managedRoles = new HashSet<>();
        for (RoleDTO rdto : userDTO.getRoles()) {
            String name = userDTO != null && userDTO.getRoles() != null && !userDTO.getRoles().isEmpty() ? rdto.getName().toUpperCase() : "USER";
            Role existingRole = roleRepository.findByName(name);
            if (existingRole != null) {
                managedRoles.add(existingRole);
            }
        }
        User user1 = repo.findByEmail(user.getEmail());
        User user2 = null;
        User user3 = repo.findByContactNumber(user.getContactNumber());
        GeneralHttpResponseDTO<UserDTO> generalHttpResponseDTO = new GeneralHttpResponseDTO<>();
        if((user1 == null || user1.getUserId() == null) && (user3 == null || user3.getUserId() == null)){
            user.setRoles(managedRoles);
            user2 = repo.save(user);
            String mainRole = fetchRoleName(user2);
            generalHttpResponseDTO.setRoleName(mainRole);
            generalHttpResponseDTO.setResponseCode(201);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setToken(tokenProvider.generateToken(user2.getUuid(),mainRole));
            generalHttpResponseDTO.setResponseBody(mapper.convertUserToDto(user2));
            generalHttpResponseDTO.setResponseMessage("Successfully saved new User");
        }else if(user1 != null && user1.getUserId() != null){
            user.setUserId(user1.getUserId());
            user.setUuid(user1.getUuid());
            user.setRoles(managedRoles);
            user2 = repo.save(user);
            String mainRole = fetchRoleName(user2);
            generalHttpResponseDTO.setRoleName(mainRole);
            generalHttpResponseDTO.setResponseCode(200);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setToken(tokenProvider.generateToken(user2.getUuid(),mainRole));
            generalHttpResponseDTO.setResponseBody(mapper.convertUserToDto(user2));
            generalHttpResponseDTO.setResponseMessage("Successfully update User data by email");
        } else if (user3 != null && user3.getUserId() != null) {
            user.setUserId(user3.getUserId());
            user.setUuid(user3.getUuid());
            user.setRoles(managedRoles);
            user2 = repo.save(user);
            String mainRole = fetchRoleName(user2);
            generalHttpResponseDTO.setRoleName(mainRole);
            generalHttpResponseDTO.setResponseCode(200);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setToken(tokenProvider.generateToken(user2.getUuid(),mainRole));
            generalHttpResponseDTO.setResponseBody(mapper.convertUserToDto(user2));
            generalHttpResponseDTO.setResponseMessage("Successfully update User data by contactNumber");
        }
        return generalHttpResponseDTO;
    }

    public GeneralHttpResponseDTO<UserDTO>fetchDataByUuid(String uuid){
        GeneralHttpResponseDTO<UserDTO> generalHttpResponseDTO = new GeneralHttpResponseDTO<>();
        User user = repo.findByUuid(uuid);
        String mainRole = fetchRoleName(user);
        if(user != null){
            generalHttpResponseDTO.setUuid(user.getUuid());
            generalHttpResponseDTO.setToken(tokenProvider.generateToken(user.getUuid(),mainRole));
        }
        generalHttpResponseDTO.setRoleName(mainRole);
        generalHttpResponseDTO.setResponseCode(200);
        generalHttpResponseDTO.setDate(new Date());
        generalHttpResponseDTO.setResponseBody(mapper.convertUserToDto(user));
        return generalHttpResponseDTO;
    }

    public GeneralHttpResponseDTO<OrderDTO> placeOrder(OrderDTO dto){
        GeneralHttpResponseDTO<OrderDTO> generalHttpResponseDTO = new GeneralHttpResponseDTO<>();
        String token = securityUtil.getCurrentToken();
        String uid = tokenProvider.getUuidFromToken(token);
        GeneralHttpResponseDTO<UserDTO> uuid =  fetchDataByUuid(uid);
        dto.setUserId(uuid.getResponseBody().getUuid());
        GeneralHttpResponseDTO<List<OrderDTO>> list = order.fetchAllOrders();
        GeneralHttpResponseDTO<OrderDTO> placeOrder = order.addProduct(dto);
        if(placeOrder != null && placeOrder.getResponseBody() != null){
            OrderDTO item = placeOrder.getResponseBody();
            generalHttpResponseDTO.setResponseBody(item);
            generalHttpResponseDTO.setResponseMessage("Order placed successfully");
            generalHttpResponseDTO.setResponseCode(200);
        }else{
            generalHttpResponseDTO.setResponseMessage("Unable to placed Order");
            generalHttpResponseDTO.setResponseCode(400);
        }
        generalHttpResponseDTO.setDate(new Date());
        return generalHttpResponseDTO;
    }
    public GeneralHttpResponseDTO<List<UserDTO>> fetchUsersList(){
        GeneralHttpResponseDTO<List<UserDTO>> generalHttpResponseDTO = new GeneralHttpResponseDTO<>();
        String token = securityUtil.getCurrentToken();
        String uuid = tokenProvider.getUuidFromToken(token);
        User user = new User();
        try {
           user = repo.findByUuid(uuid);
        }catch (ExceptionDTO ex){
            throw new ExceptionDTO("User is not exist with this " + uuid + " id.",new Date(), HttpStatus.NOT_FOUND,null);
        }
        String mainRole = fetchRoleName(user);
        List<User> userList = repo.findAll();
        generalHttpResponseDTO.setResponseCode(200);
        generalHttpResponseDTO.setDate(new Date());
        generalHttpResponseDTO.setRoleName(mainRole);
        generalHttpResponseDTO.setUuid(uuid);
        generalHttpResponseDTO.setToken(token);
        generalHttpResponseDTO.setResponseBody(mapper.convertUserToDtoList(userList));
        return generalHttpResponseDTO;
    }

    public GeneralHttpResponseDTO<UserDTO> fetchDataById(Long userId){
        User user = repo.findById(userId).orElseThrow(
                ()->new ExceptionDTO("User is not exist with this " + userId + " id.",new Date(), HttpStatus.BAD_GATEWAY,null)
        );
        GeneralHttpResponseDTO<UserDTO> generalHttpResponseDTO = new GeneralHttpResponseDTO<>();
        if(user != null && user.getUserId() != null){
            String mainRole = fetchRoleName(user);
            generalHttpResponseDTO.setRoleName(mainRole);
            generalHttpResponseDTO.setToken(tokenProvider.generateToken(user.getUuid(),mainRole));
            generalHttpResponseDTO.setResponseCode(200);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setResponseBody(mapper.convertUserToDto(user));
        }
        return generalHttpResponseDTO;
    }

    public GeneralHttpResponseDTO<String> deleteDataById(Long userId){
        GeneralHttpResponseDTO<UserDTO> generalHttpResponse = fetchDataById(userId);
        GeneralHttpResponseDTO<String> generalHttpResponseDTO = new GeneralHttpResponseDTO<>();
        UserDTO userDTO = generalHttpResponse.getResponseBody();
        if(userDTO != null && userDTO.getUserId() != null){
            repo.deleteById(userDTO.getUserId());
            generalHttpResponseDTO.setResponseCode(200);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setResponseMessage("User Deleted Successfully");
        }else if(userDTO == null || userDTO.getUserId() == null){
            generalHttpResponseDTO.setResponseCode(403);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setResponseMessage("Unable to delete data because User is not exist " + userId);
        }
        return generalHttpResponseDTO;
    }

    public GeneralHttpResponseDTO<List<CustomInventoryDTO>>loginUser(SignInDTO userDTO){
        GeneralHttpResponseDTO<List<CustomInventoryDTO>> generalHttpResponseDTO = new GeneralHttpResponseDTO<>();
        User user = repo.findByEmail(userDTO.getEmail());
        String password = utilities.base64Decode(user.getPassword());
        boolean result = userDTO.getPassword().equals(password);
        ResponseEntity<List<InventoryDetailsDTO>> inventoryDTOList = inventory.fetchInventoryList();
        List<InventoryDetailsDTO> inventoryDTOS = inventoryDTOList.getBody();
        if(user != null && user.getUserId() != null && user.getEmail().equals(userDTO.getEmail()) && result){
            String mainRole = fetchRoleName(user);
            generalHttpResponseDTO.setRoleName(mainRole);
            generalHttpResponseDTO.setToken(tokenProvider.generateToken(user.getUuid(),mainRole));
            generalHttpResponseDTO.setResponseCode(200);
            generalHttpResponseDTO.setDate(new Date());

            if(inventoryDTOS != null && !inventoryDTOS.isEmpty()){
                List<CustomInventoryDTO> list = inventoryMapper.convertInventoryToDtoCustomList(inventoryDTOS);
                generalHttpResponseDTO.setResponseBody(list);
                generalHttpResponseDTO.setResponseMessage("Showing Products list");
            }
        }else{
            generalHttpResponseDTO.setResponseCode(403);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setResponseMessage("User is not exist with this email : " + userDTO.getEmail() + ", please enter valid details.");
        }
        return generalHttpResponseDTO;
    }

    private String fetchRoleName(User user){
        return user.getRoles().stream()
                .map(Role::getName)
                .map(String::toUpperCase)
                .findFirst()
                .orElse("ROLE_USER");
    }
}
