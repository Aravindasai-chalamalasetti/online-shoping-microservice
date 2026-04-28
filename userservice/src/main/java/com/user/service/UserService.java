package com.user.service;

import com.user.converter.InventoryMapper;
import com.user.converter.UserMapper;
import com.user.dto.*;
import com.user.model.GenericDetails;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public GeneralHttpResponseDTO<UserDTO> saveUserData(UserDTO userDTO, String operationType) {
        User user = mapper.convertDtoToUser(userDTO);
        user.setRoles(resolveRoles(userDTO.getRoles()));

        User existingByEmail = repo.findByEmail(user.getEmail());
        User existingByContact = repo.findByContactNumber(user.getContactNumber());

        boolean isSignUp = "signUpUser".equals(operationType);
        boolean isUpdate = "updateUser".equals(operationType);

        if (isSignUp) {
            return handleSignUp(user, existingByEmail, existingByContact);
        } else if (isUpdate) {
            return handleUpdate(user, existingByEmail, existingByContact);
        }

        return buildErrorResponse("Invalid operation type: " + operationType, HttpStatus.BAD_REQUEST);
    }

    private GeneralHttpResponseDTO<UserDTO> handleSignUp(User user, User existingByEmail, User existingByContact) {
        if (isUserExists(existingByEmail)) {
            return buildErrorResponse("Email already exists. Please use different email.", HttpStatus.CONFLICT);
        }
        if (isUserExists(existingByContact)) {
            return buildErrorResponse("Contact number already exists. Please use different number.", HttpStatus.CONFLICT);
        }

        User savedUser = repo.save(user);
        return buildUserSuccessResponse(savedUser, "Successfully saved new User", 201);
    }

    private GeneralHttpResponseDTO<UserDTO> handleUpdate(User user, User existingByEmail, User existingByContact) {
        User userToUpdate = determineUserToUpdate(existingByEmail, existingByContact);

        if (userToUpdate == null) {
            return buildErrorResponse("User not found for update.", HttpStatus.NOT_FOUND);
        }

        /*Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        GenericDetails genericDetails = user.getGenericDetails();
        if (genericDetails == null) {
            genericDetails = new GenericDetails();
        }
        genericDetails.setModifiedTime(now);

        if (genericDetails.getCreatedBy() == null || genericDetails.getCreatedBy().isEmpty() || genericDetails.getModifiedBy() == null || genericDetails.getModifiedBy().isEmpty()) {
            genericDetails.setCreatedBy("system");
            genericDetails.setModifiedBy(genericDetails.getCreatedBy());
        }
        if (genericDetails.getCreatedTime() == null) {
            genericDetails.setCreatedTime(userToUpdate.getGenericDetails() != null
                    ? userToUpdate.getGenericDetails().getCreatedTime()
                    : now);
        }
        //user.setGenericDetails(genericDetails);*/

        // Preserve ID and UUID
        user.setUserId(userToUpdate.getUserId());
        user.setUuid(userToUpdate.getUuid());

        User savedUser = repo.save(user);
        return buildUserSuccessResponse(savedUser, "Successfully updated User data", 200);
    }

    private Set<Role> resolveRoles(Set<RoleDTO> roleDTOs) {
        Set<Role> managedRoles = new HashSet<>();

        if (roleDTOs == null || roleDTOs.isEmpty()) {
            Role defaultRole = roleRepository.findByName("USER");
            if (defaultRole != null) managedRoles.add(defaultRole);
            return managedRoles;
        }

        for (RoleDTO rdto : roleDTOs) {
            String roleName = (rdto.getName() != null ? rdto.getName().toUpperCase() : "USER");
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                managedRoles.add(role);
            }
        }
        return managedRoles;
    }

    private boolean isUserExists(User user) {
        return user != null && user.getUserId() != null;
    }

    private User determineUserToUpdate(User byEmail, User byContact) {
        if (isUserExists(byEmail)) return byEmail;
        if (isUserExists(byContact)) return byContact;
        return null;
    }

    private GeneralHttpResponseDTO<UserDTO> buildUserSuccessResponse(User user, String message, int code) {
        String roleName = fetchRoleName(user);

        GeneralHttpResponseDTO<UserDTO> response = new GeneralHttpResponseDTO<>();
        response.setResponseCode(code);
        response.setResponseMessage(message);
        response.setDate(new Date());
        response.setRoleName(roleName);
        response.setUuid(user.getUuid());
        response.setToken(tokenProvider.generateToken(user.getUuid(), roleName));
        response.setResponseBody(mapper.convertUserToDto(user));

        return response;
    }

    private GeneralHttpResponseDTO<UserDTO> buildErrorResponse(String message, HttpStatus status) {
        GeneralHttpResponseDTO<UserDTO> response = new GeneralHttpResponseDTO<>();
        response.setResponseCode(status.value());
        response.setResponseMessage(message);
        response.setDate(new Date());
        return response;
    }

    public GeneralHttpResponseDTO<UserDTO> fetchDataByUuid(String uuid) {
        User user = repo.findByUuid(uuid);
        if (user == null) {
            return buildErrorResponse("User not found with UUID: " + uuid, HttpStatus.NOT_FOUND);
        }
        return buildUserSuccessResponse(user, "User fetched successfully", 200);
    }

    public GeneralHttpResponseDTO<UserDTO> fetchDataById(Long userId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ExceptionDTO("User not found with ID: " + userId,
                        new Date(), HttpStatus.NOT_FOUND, null));

        return buildUserSuccessResponse(user, "User fetched successfully", 200);
    }

    public GeneralHttpResponseDTO<List<UserDTO>> fetchUsersList() {
        // Note: This method currently requires token.
        // If you want to make it public, remove token logic or make it optional.
        String token = securityUtil.getCurrentToken();
        String uuid = tokenProvider.getUuidFromToken(token);

        User currentUser = repo.findByUuid(uuid);
        if (currentUser == null) {
            throw new ExceptionDTO("Current user not found", new Date(), HttpStatus.NOT_FOUND, null);
        }

        List<User> users = repo.findAll();
        String roleName = fetchRoleName(currentUser);

        GeneralHttpResponseDTO<List<UserDTO>> response = new GeneralHttpResponseDTO<>();
        response.setResponseCode(200);
        response.setDate(new Date());
        response.setRoleName(roleName);
        response.setUuid(uuid);
        response.setToken(token);
        response.setResponseBody(mapper.convertUserToDtoList(users));

        return response;
    }


    public GeneralHttpResponseDTO<String> deleteDataById(Long userId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new ExceptionDTO("User not found with ID: " + userId,
                        new Date(), HttpStatus.NOT_FOUND, null));

        repo.deleteById(userId);

        GeneralHttpResponseDTO<String> response = new GeneralHttpResponseDTO<>();
        response.setResponseCode(200);
        response.setDate(new Date());
        response.setResponseMessage("User deleted successfully");
        return response;
    }

    // ====================== PLACE ORDER ======================
    public GeneralHttpResponseDTO<OrderDTO> placeOrder(OrderDTO dto) {
        String token = securityUtil.getCurrentToken();
        String uid = tokenProvider.getUuidFromToken(token);
        String userUid = dto != null && dto.getUserId() != null && !dto.getUserId().isEmpty() ? dto.getUserId() : uid;
        // Fetch user to validate existence
        GeneralHttpResponseDTO<UserDTO> userResponse = fetchDataByUuid(userUid);
        if (userResponse.getResponseBody() == null) {
            return buildOrderErrorResponse("User not found", HttpStatus.NOT_FOUND);
        }

        dto.setUserId(userResponse.getResponseBody().getUuid());
        GeneralHttpResponseDTO<OrderDTO> placeOrderResponse = order.addProduct(dto);

        if (placeOrderResponse != null && placeOrderResponse.getResponseBody() != null) {
            return buildOrderSuccessResponse(placeOrderResponse.getResponseBody(), "Order placed successfully");
        } else {
            return buildOrderErrorResponse("Unable to place order", HttpStatus.BAD_REQUEST);
        }
    }

    private GeneralHttpResponseDTO<OrderDTO> buildOrderSuccessResponse(OrderDTO order, String message) {
        GeneralHttpResponseDTO<OrderDTO> response = new GeneralHttpResponseDTO<>();
        GeneralHttpResponseDTO<UserDTO> user = fetchDataByUuid(order.getUserId());
        User  userInfo = user != null && user.getResponseBody() != null && user.getResponseBody().getUserId() != null ? mapper.convertDtoToUser(user.getResponseBody()) : null;
        String roleName = fetchRoleName(userInfo);
        response.setUuid(order.getUserId());
        response.setToken(tokenProvider.generateToken(order.getUserId(), roleName));
        response.setResponseCode(200);
        response.setResponseMessage(message);
        response.setDate(new Date());
        response.setResponseBody(order);
        return response;
    }

    private GeneralHttpResponseDTO<OrderDTO> buildOrderErrorResponse(String message, HttpStatus status) {
        GeneralHttpResponseDTO<OrderDTO> response = new GeneralHttpResponseDTO<>();
        response.setResponseCode(status.value());
        response.setResponseMessage(message);
        response.setDate(new Date());
        return response;
    }

    // ====================== LOGIN ======================
    public GeneralHttpResponseDTO<List<CustomInventoryDTO>> loginUser(SignInDTO userDTO) {
        User user = repo.findByEmail(userDTO.getEmail());

        if (user == null || !utilities.base64Decode(user.getPassword()).equals(userDTO.getPassword())) {
            GeneralHttpResponseDTO<List<CustomInventoryDTO>> error = new GeneralHttpResponseDTO<>();
            error.setResponseCode(403);
            error.setDate(new Date());
            error.setResponseMessage("Invalid email or password");
            return error;
        }

        String mainRole = fetchRoleName(user);
        ResponseEntity<List<InventoryDetailsDTO>> inventoryResponse = inventory.fetchInventoryList();
        List<CustomInventoryDTO> inventoryList = inventoryMapper.convertInventoryToDtoCustomList(
                inventoryResponse.getBody() != null ? inventoryResponse.getBody() : List.of()
        );

        GeneralHttpResponseDTO<List<CustomInventoryDTO>> response = new GeneralHttpResponseDTO<>();
        response.setResponseCode(200);
        response.setDate(new Date());
        response.setRoleName(mainRole);
        response.setUuid(user.getUuid());
        response.setToken(tokenProvider.generateToken(user.getUuid(), mainRole));
        response.setResponseBody(inventoryList);
        response.setResponseMessage("Login successful. Products list retrieved.");

        return response;
    }

    // ====================== PRIVATE UTILITY ======================
    private String fetchRoleName(User user) {
        if (user == null || user.getRoles() == null || user.getRoles().isEmpty()) {
            return "ROLE_USER";
        }
        return user.getRoles().stream()
                .map(Role::getName)
                .map(String::toUpperCase)
                .findFirst()
                .orElse("ROLE_USER");
    }
}
