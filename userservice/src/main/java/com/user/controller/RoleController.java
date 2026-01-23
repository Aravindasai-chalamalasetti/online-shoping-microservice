package com.user.controller;

import com.user.dto.GeneralHttpResponseDTO;
import com.user.dto.RoleDTO;
import com.user.dto.UserDTO;
import com.user.model.Role;
import com.user.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/save")
    public RoleDTO saveUser(@Valid @RequestBody RoleDTO roleDTO){
        return roleService.saveRole(roleDTO);
    }

    @GetMapping("/fetchRoles")
    public List<Role> fetchRoleList(){
        return roleService.fetchRoleList();
    }

    @GetMapping("/fetchRoleById/{roleId}")
    public RoleDTO findByRoleId(@PathVariable Long roleId){
        return roleService.fetchDataByRoleId(roleId);
    }

    @PutMapping("/updateUser")
    public RoleDTO updateRole(@Valid @RequestBody RoleDTO roleDTO){
        return roleService.saveRole(roleDTO);
    }

    @DeleteMapping("/deleteRole")
    public GeneralHttpResponseDTO<String> deleteRole(@RequestParam Long roleId){
        return roleService.deleteRoleDataById(roleId);
    }
}
