package com.user.controller;

import com.user.dto.*;
import com.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public GeneralHttpResponseDTO<List<CustomInventoryDTO>> loginUser(@Valid @RequestBody SignInDTO userDTO){
        return userService.loginUser(userDTO);
    }

    @PostMapping("/sign-up")
    public GeneralHttpResponseDTO<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO){
        return userService.saveUserData(userDTO,"signUpUser");
    }

    @GetMapping("/findUid/{userId}")
    public GeneralHttpResponseDTO<UserDTO> findUuid(@PathVariable String userId){
        GeneralHttpResponseDTO<UserDTO> user = userService.fetchDataByUuid(userId);
        System.out.println("---find--uuid--data---- : " + user);
        return user;
    }

    @PostMapping("/place-order")
    public GeneralHttpResponseDTO<OrderDTO> saveUser(@Valid @RequestBody OrderDTO userDTO){
        return userService.placeOrder(userDTO);
    }

    @GetMapping("/fetchUsers")
    public GeneralHttpResponseDTO<List<UserDTO>> fetchUsersList(){
        return userService.fetchUsersList();
    }

    @GetMapping("/fetchUserById/{userId}")
    public GeneralHttpResponseDTO<UserDTO> findUserById(@PathVariable Long userId){
        return userService.fetchDataById(userId);
    }

    @PutMapping("/updateUser")
    public GeneralHttpResponseDTO<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO){
        return userService.saveUserData(userDTO,"updateUser");
    }

    @DeleteMapping("/deleteUser")
    public GeneralHttpResponseDTO<String> deleteUser(@RequestParam Long userId){
        return userService.deleteDataById(userId);
    }
}
