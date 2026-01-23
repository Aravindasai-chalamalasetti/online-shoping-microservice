package com.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Component
@Embeddable
public class GenericDetails {
    @NotEmpty(message = "createdBy can't be empty")
    @Column(nullable = false, updatable = false)
    private String createdBy;
    @NotEmpty(message = "createdTime can't be empty")
    @Column(nullable = false,updatable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm a")
    private Timestamp createdTime;

    private String modifiedBy;
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm a")
    private Timestamp modifiedTime;

    public GenericDetails(String createdBy, Timestamp createdTime, String modifiedBy, Timestamp modifiedTime) {
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.modifiedBy = modifiedBy;
        this.modifiedTime = modifiedTime;
    }

    @PrePersist
    protected void onCreate() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        if (createdBy == null) createdBy = "system";
        if (modifiedBy == null) modifiedBy = createdBy;
        createdTime = now;
        modifiedTime = now;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedTime = Timestamp.valueOf(LocalDateTime.now());
    }
    public GenericDetails() {}

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

}
