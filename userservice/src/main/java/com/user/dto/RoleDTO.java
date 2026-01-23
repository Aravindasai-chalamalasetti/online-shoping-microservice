package com.user.dto;

import com.user.model.User;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleDTO {
    private Long roleId;

    @NotNull(message = "roleName cannot be empty")
    @Pattern(
            regexp = "^(?i)(ADMIN|USER|ROLE_ADMIN|ROLE_USER)$",
            message = "Role name must be admin, user, Role_User,Role_Admin"
    )
    private String name;


    public RoleDTO(RoleDTOBuilder dtoBuilder) {
        this.roleId = dtoBuilder.roleId;
        this.name = dtoBuilder.name;
    }

    public RoleDTO(){}
    public Long getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public static class RoleDTOBuilder{
        private Long roleId;

        @NotNull(message = "roleName cannot be empty")
        @Pattern(
                regexp = "^(?i)(ADMIN|USER|ROLE_ADMIN|ROLE_USER)$",
                message = "Role name must be admin, user, Role_User,Role_Admin"
        )
        private String name;

        public RoleDTOBuilder(){}

        public RoleDTOBuilder setRoleId(Long roleId) {
            this.roleId = roleId;return this;
        }

        public RoleDTOBuilder setName(String name) {
            this.name = name;return this;
        }

        public RoleDTO build(){
            return new RoleDTO(this);
        }
    }
}
