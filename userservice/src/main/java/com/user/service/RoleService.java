package com.user.service;

import com.user.converter.RoleMapper;
import com.user.dto.ExceptionDTO;
import com.user.dto.GeneralHttpResponseDTO;
import com.user.dto.RoleDTO;
import com.user.model.Role;
import com.user.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repo;

    private final RoleMapper mapper;

    public RoleService(RoleRepository repo, RoleMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public RoleDTO saveRole(RoleDTO roleDTO){
        Role role = mapper.convertDtoToRole(roleDTO);
        String roleName = roleDTO != null && roleDTO.getName() != null ? roleDTO.getName() : " ";
        Role findRoleData = repo.findByName(roleName);
        boolean updateResult = role != null && findRoleData !=null && role.getRoleId() != null && role.getRoleId().equals(findRoleData.getRoleId()) ? true : false;
        Role saveRole = null;
        if(findRoleData == null || findRoleData.getRoleId() == null) {
             saveRole = repo.save(role);
        }else if(findRoleData.getRoleId() != null && updateResult){
            saveRole = repo.save(role);
        }
        return mapper.convertRoleToDTO(saveRole);
    }

    public List<Role> fetchRoleList(){
        return repo.findAll();
    }

    public RoleDTO fetchDataByRoleId(Long roleId){
        Role role = repo.findById(roleId).orElseThrow(
                ()->new ExceptionDTO("Role is not exist with this " + roleId + " id.",new Date(), HttpStatus.BAD_GATEWAY,null)
        );
        return mapper.convertRoleToDTO(role);
    }

    public GeneralHttpResponseDTO<String> deleteRoleDataById(Long roleId){
        GeneralHttpResponseDTO<String> generalHttpResponseDTO = new GeneralHttpResponseDTO<>();
        Role role = repo.findByRoleId(roleId);
        if(role != null && role.getRoleId() != null){
            repo.deleteById(role.getRoleId());
            generalHttpResponseDTO.setResponseCode(200);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setResponseMessage("Role Deleted Successfully");
        }else if(role == null || role.getRoleId() == null){
            generalHttpResponseDTO.setResponseCode(403);
            generalHttpResponseDTO.setDate(new Date());
            generalHttpResponseDTO.setResponseMessage("Unable to delete data because Role is not exist with this " + roleId + " id.");
        }
        return generalHttpResponseDTO;
    }
}
