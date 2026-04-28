package com.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Embeddable
public class GenericDetails {
    @NotEmpty(message = "createdBy can't be empty")
    @Column(nullable = false, updatable = false)
    private String createdBy;
    @NotEmpty(message = "createdTime can't be empty")
    @Column(nullable = false,updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a",timezone = "Asia/Kolkata")
    private Timestamp createdTime;

    private String modifiedBy;
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a",timezone = "Asia/Kolkata")
    private Timestamp modifiedTime;

    public GenericDetails(String createdBy, Timestamp createdTime, String modifiedBy, Timestamp modifiedTime) {
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.modifiedBy = modifiedBy;
        this.modifiedTime = modifiedTime;
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedTime = Timestamp.valueOf(LocalDateTime.now());
        if (this.modifiedBy == null || this.modifiedBy.trim().isEmpty()) {
            this.modifiedBy = this.createdBy != null ? this.createdBy : "system";
        }
    }

    public GenericDetails() {}

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    public Timestamp getCreatedTime() {
        return createdTime;
    }
    @JsonFormat
    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    public Timestamp getModifiedTime() {
        return modifiedTime;
    }
    @JsonFormat
    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

}
