package com.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.user.utils.MultiDateDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_information")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotEmpty(message = "uuid can't be null")
    @Column(unique = true, nullable = false,updatable = false)
    private String uuid;

    @NotEmpty(message = "firstName can't be null")
    @Size(min = 3, message = "use minimum 3 character's for firstName")
    @Size(max = 30, message = "not use more than 30 character's for firstName")
    @Column(nullable = false)
    private String firstName;

    private String lastName;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format. (Use format like: name@domain.com")
    @Size(min = 6,max = 45, message = "Email must be less than 45 characters")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "gender can't be null")
    @Pattern(
            regexp = "^(?i)(male|female|others)$",
            message = "Gender must be male, female, others"
    )
    @Column(nullable = false)
    private String gender;
    @Pattern(
            regexp = "^\\+[1-9]{1}[0-9]{1,3}[0-9]{10}$",
            message = "Phone number must include country code and 10 digits"
    )
    // +91XXXXXXXXXX
    @NotNull(message = "Contact Number cannot be empty")
    @Column(nullable = false, unique = true)
    private String contactNumber;
    @NotNull(message = "Date can't be null")
    @JsonDeserialize(using = MultiDateDeserializer.class)
   @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @NotEmpty(message = "password can't be null")
    @Size(min = 8, message = "use minimum 8 character's for password")
    @Size(max = 25, message = "password length must be less than  25 character's")
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean active = true;

    @Embedded
    private GenericDetails genericDetails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(Long userId, String uuid, String firstName, String lastName, String email, String gender, String contactNumber, Date dateOfBirth, String password, Boolean active, GenericDetails genericDetails, Set<Role> roles) {
        this.userId = userId;
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.active = active;
        this.genericDetails = genericDetails;
        this.roles = roles;
    }

    public User(){}


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public GenericDetails getGenericDetails() {
        return genericDetails;
    }

    public void setGenericDetails(GenericDetails genericDetails) {
        this.genericDetails = genericDetails;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
