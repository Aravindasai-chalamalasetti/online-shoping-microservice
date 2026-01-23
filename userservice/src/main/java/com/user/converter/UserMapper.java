package com.user.converter;

import com.user.dto.GenericDetailsDTO;
import com.user.dto.RoleDTO;
import com.user.dto.UserDTO;
import com.user.model.GenericDetails;
import com.user.model.Role;
import com.user.model.User;
import com.user.utils.Utilities;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final Utilities utilities;

    public UserMapper(Utilities utilities) {
        this.utilities = utilities;
    }

    public UserDTO convertUserToDto(User user){
        UserDTO userDTO = new UserDTO.UserDTOBuilder()
                .setActive(user.getActive())
                .setContactNumber(user.getContactNumber())
                .setEmail(user.getEmail())
                .setDateOfBirth(user.getDateOfBirth())
                .setFirstName(user.getFirstName())
                .setGender(user.getGender())
                .setGenericDetails(convertGenericDetailsToDto(user.getGenericDetails()))
                .setLastName(user.getLastName())
                .setPassword(utilities.base64Decode(user.getPassword()))
                .setUserId(user.getUserId())
                .setUuid(user.getUuid())
                .setRoles(convertRoleToDto(user.getRoles()))
                .build();
        return userDTO;
    }

    public GenericDetailsDTO convertGenericDetailsToDto(GenericDetails genericDetails){
        GenericDetailsDTO genericDetailsDTO = new GenericDetailsDTO.GenericDetailsDTOBuilder()
                .setCreatedBy(genericDetails.getCreatedBy())
                .setCreatedTime(genericDetails.getCreatedTime())
                .setModifiedBy(genericDetails.getModifiedBy())
                .setModifiedTime(genericDetails.getModifiedTime())
                .build();
        return genericDetailsDTO;
    }

    public User convertDtoToUser(UserDTO userDTO){
        String uuid = userDTO != null && userDTO.getUuid() == null ? utilities.generateUuid(userDTO.getEmail()) : userDTO.getUuid();
        User user = new User();
        user.setActive(userDTO.getActive());
        user.setContactNumber(userDTO.getContactNumber());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setUuid(uuid);
        user.setUserId(userDTO.getUserId());
        user.setPassword(utilities.base64Encode(userDTO.getPassword()));
        user.setLastName(userDTO.getLastName());
        user.setFirstName(userDTO.getFirstName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setGenericDetails(convertDtoToGenericDetails(userDTO.getGenericDetails()));
        user.setRoles(convertDtoToRole(userDTO.getRoles()));
        return user;
    }

    private Set<Role> convertDtoToRole(Set<RoleDTO> roleDTOs){
        if (roleDTOs == null || roleDTOs.isEmpty()) {
            Set<Role> defaultRoles = new HashSet<>();
            Role defaultRole = new Role();
            defaultRole.setName("USER");
            defaultRoles.add(defaultRole);
            return defaultRoles;
        }

        return roleDTOs.stream()
                .map((r)->convertRoleDtoToEntity(r))
                .collect(Collectors.toSet());
    }

    private Role convertRoleDtoToEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleId(roleDTO.getRoleId());
        role.setName(roleDTO.getName() != null ? roleDTO.getName().toUpperCase() : "USER");
        return role;
    }

    public Set<RoleDTO> convertRoleToDto(Set<Role> role) {
        if (role == null || role.isEmpty()) return null;

        return role.stream().map(dto-> {
            RoleDTO roleDto = new RoleDTO.RoleDTOBuilder()
                    .setRoleId(dto.getRoleId())
                    .setName(dto.getName())
                    .build();
            return roleDto;
        }).collect(Collectors.toSet());
    }
    public GenericDetails convertDtoToGenericDetails(GenericDetailsDTO dto){
        GenericDetails genericDetails = new GenericDetails();
        genericDetails.setCreatedBy(dto.getCreatedBy());
        genericDetails.setCreatedTime(dto.getCreatedTime());
        genericDetails.setModifiedBy(dto.getModifiedBy() == null && dto.getModifiedBy().isEmpty() ? dto.getCreatedBy() : dto.getModifiedBy());
        genericDetails.setModifiedTime(dto.getModifiedTime());
        return genericDetails;
    }

    public List<UserDTO> convertUserToDtoList(List<User> userList){
        List<UserDTO> userDTOList = userList.stream().map((u)->mapUserToDto(u)).collect(Collectors.toList());
        return userDTOList;
    }

    private UserDTO mapUserToDto(User u) {
        return convertUserToDto(u);
    }
}
