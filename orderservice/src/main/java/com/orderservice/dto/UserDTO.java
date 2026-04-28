package com.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.orderservice.util.MultiDateDeserializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class UserDTO {
    private Long userId;

    private String uuid;

    @NotEmpty(message = "firstName can't be null")
    @Size(min = 3, message = "use minimum 3 character's for firstName")
    @Size(max = 30, message = "not use more than 30 character's for firstName")
    private String firstName;

    private String lastName;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format. (Use format like: name@domain.com")
    @Size(min = 6,max = 45, message = "Email must be less than 45 characters")
    private String email;

    @NotEmpty(message = "gender can't be null")
    @Pattern(
            regexp = "^(?i)(male|female|others)$",
            message = "Gender must be male, female, others"
    )
    private String gender;
    @Pattern(
            regexp = "^\\+[1-9]{1}[0-9]{1,3}[0-9]{10}$",
            message = "Phone number must include country code and 10 digits"
    )
    // +91XXXXXXXXXX
    @NotNull(message = "Contact Number cannot be empty")
    private String contactNumber;
    @NotNull(message = "Date can't be null")
    @JsonDeserialize(using = MultiDateDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    private Date dateOfBirth;

    @NotEmpty(message = "password can't be null")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*!]).{8,25}$",
            message = "Password must be 8-25 chars with at least 1 digit, 1 lowercase, 1 uppercase, 1 special (@#$%^&+=), no whitespace"
    )
    private String password;

    private Boolean active = true;

    private GenericDetailsDTO genericDetails;

    private Set<RoleDTO> roles;

    public UserDTO(UserDTOBuilder userDTOBuilder) {
        this.userId = userDTOBuilder.userId;
        this.uuid = userDTOBuilder.uuid;
        this.firstName = userDTOBuilder.firstName;
        this.lastName = userDTOBuilder.lastName;
        this.email = userDTOBuilder.email;
        this.gender = userDTOBuilder.gender;
        this.contactNumber = userDTOBuilder.contactNumber;
        this.dateOfBirth = userDTOBuilder.dateOfBirth;
        this.password = userDTOBuilder.password;
        this.active = userDTOBuilder.active;
        this.genericDetails = userDTOBuilder.genericDetails;
        this.roles = userDTOBuilder.roles;
    }

    public UserDTO(){}

    public Long getUserId() {
        return userId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getActive() {
        return active;
    }

    public GenericDetailsDTO getGenericDetails() {
        return genericDetails;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public static class UserDTOBuilder{
        private Long userId;

        private String uuid;

        @NotEmpty(message = "firstName can't be null")
        @Size(min = 3, message = "use minimum 3 character's for firstName")
        @Size(max = 30, message = "not use more than 30 character's for firstName")
        private String firstName;

        private String lastName;
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format. (Use format like: name@domain.com")
        @Size(min = 6,max = 45, message = "Email must be less than 45 characters")
        private String email;

        @NotEmpty(message = "gender can't be null")
        @Pattern(
                regexp = "^(?i)(male|female|others)$",
                message = "Gender must be male, female, others"
        )
        private String gender;
        @Pattern(
                regexp = "^\\+[1-9]{1}[0-9]{1,3}[0-9]{10}$",
                message = "Phone number must include country code and 10 digits"
        )
        // +91XXXXXXXXXX
        @NotNull(message = "Contact Number cannot be empty")
        private String contactNumber;
        @NotNull(message = "Date can't be null")
        @JsonDeserialize(using = MultiDateDeserializer.class)
        @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
        private Date dateOfBirth;

        @NotEmpty(message = "password can't be null")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*!]).{8,25}$",
                message = "Password must be 8-25 chars with at least 1 digit, 1 lowercase, 1 uppercase, 1 special (@#$%^&+=), no whitespace"
        )
        private String password;

        private Boolean active = true;

        private GenericDetailsDTO genericDetails;

        private Set<RoleDTO> roles;

        public UserDTOBuilder(){}

        public UserDTOBuilder setUserId(Long userId) {
            this.userId = userId;return this;
        }

        public UserDTOBuilder setUuid(String uuid) {
            this.uuid = uuid;return this;
        }

        public UserDTOBuilder setFirstName(String firstName) {
            this.firstName = firstName;return this;
        }

        public UserDTOBuilder setLastName(String lastName) {
            this.lastName = lastName;return this;
        }

        public UserDTOBuilder setEmail(String email) {
            this.email = email;return this;
        }

        public UserDTOBuilder setGender(String gender) {
            this.gender = gender;return this;
        }

        public UserDTOBuilder setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;return this;
        }

        public UserDTOBuilder setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;return this;
        }

        public UserDTOBuilder setPassword(String password) {
            this.password = password;return this;
        }

        public UserDTOBuilder setActive(Boolean active) {
            this.active = active;return this;
        }

        public UserDTOBuilder setGenericDetails(GenericDetailsDTO genericDetails) {
            this.genericDetails = genericDetails;return this;
        }

        public UserDTOBuilder setRoles(Set<RoleDTO> roles) {
            this.roles = roles;return this;
        }

        public UserDTO build(){
            return new UserDTO(this);
        }
    }
}
