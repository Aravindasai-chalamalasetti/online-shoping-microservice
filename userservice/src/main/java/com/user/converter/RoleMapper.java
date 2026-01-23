package com.user.converter;

import com.user.dto.RoleDTO;
import com.user.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role convertDtoToRole(RoleDTO roleDTO){
        Role role = new Role();
        role.setRoleId(roleDTO.getRoleId());
        role.setName(roleDTO.getName());
        return role;
    }

    public RoleDTO convertRoleToDTO(Role role){
        RoleDTO roleDTO = new RoleDTO.RoleDTOBuilder()
                .setRoleId(role.getRoleId())
                .setName(role.getName()).build();
        return roleDTO;
    }
}
