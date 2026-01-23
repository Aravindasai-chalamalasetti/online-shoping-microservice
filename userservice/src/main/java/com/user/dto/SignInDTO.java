package com.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class SignInDTO {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format. (Use format like: name@domain.com")
    @Size(min = 6,max = 45, message = "Email must be less than 45 characters")
    private String email;

    @NotEmpty(message = "password can't be null")
    @Size(min = 8, message = "use minimum 8 character's for password")
    @Size(max = 25, message = "password length must be less than  25 character's")
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}",
            message = "Password must be 8-25 chars with at least 1 digit, 1 lowercase, 1 uppercase, 1 special (@#$%^&+=), no whitespace"
    )
    private String password;

    private String token;
    @NotNull(message = "roleName cannot be empty")
    @Pattern(
            regexp = "^(?i)(admin|user|Role_Admin|Role_User)$",
            message = "Role name must be admin, user, Role_User"
    )
    private String roleName;

    public SignInDTO(String email, String password, String token, String roleName) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.roleName = roleName;
    }

    public SignInDTO(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
