package com.user.converter;

import com.user.dto.*;
import com.user.model.GenericDetails;
import com.user.model.Role;
import com.user.model.User;
import com.user.utils.Utilities;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        if (user == null) return null;
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
        if (genericDetails == null) {
            return new GenericDetailsDTO();
        }
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        GenericDetailsDTO genericDetailsDTO = new GenericDetailsDTO.GenericDetailsDTOBuilder()
                .setCreatedBy(genericDetails == null || genericDetails.getCreatedBy() == null || genericDetails.getCreatedBy().isEmpty() || genericDetails.getCreatedBy().isBlank() ? "system" : genericDetails.getCreatedBy())
                .setCreatedTime(genericDetails == null || genericDetails.getCreatedTime() == null ? now : genericDetails.getCreatedTime())
                .setModifiedBy(genericDetails == null || genericDetails.getModifiedBy() == null || genericDetails.getModifiedBy().isEmpty() || genericDetails.getModifiedBy().isBlank() ? genericDetails.getCreatedBy() : genericDetails.getModifiedBy())
                .setModifiedTime(genericDetails == null || genericDetails.getModifiedTime() == null ? now : genericDetails.getModifiedTime())
                .build();
        return genericDetailsDTO;
    }

    public User convertDtoToUser(UserDTO userDTO){
        if (userDTO == null) return null;
        String uuid = userDTO != null && (userDTO.getUuid() == null || userDTO.getUuid().isEmpty() || userDTO.getUuid().isBlank()) ? utilities.generateUuid(userDTO.getEmail()) : userDTO.getUuid();
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
            Role defaultRole = new Role();
            defaultRole.setName("USER");
            return Set.of(defaultRole);
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
        if (dto == null) {
            dto = new GenericDetailsDTO();
        }
        GenericDetails genericDetails = new GenericDetails();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        genericDetails.setCreatedBy(dto == null || dto.getCreatedBy() == null || dto.getCreatedBy().isEmpty() || dto.getCreatedBy().isBlank() ? "system" : dto.getCreatedBy());
        genericDetails.setCreatedTime(dto == null || dto.getCreatedTime() == null ? now : dto.getCreatedTime());
        genericDetails.setModifiedBy(dto == null || dto.getModifiedBy() == null || dto.getModifiedBy().isEmpty() || dto.getModifiedBy().isBlank() ? dto.getCreatedBy() : dto.getModifiedBy() );
        genericDetails.setModifiedTime(dto == null || dto.getModifiedTime() == null ? now : dto.getModifiedTime());
        return genericDetails;
    }

    public List<UserDTO> convertUserToDtoList(List<User> userList){
        List<UserDTO> userDTOList = userList.stream().map((u)->convertUserToDto(u)).collect(Collectors.toList());
        return userDTOList;
    }
}
