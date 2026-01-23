package com.orderservice.dto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Embeddable
public class GenericDetailsDTO {

    private String createdBy = "system";
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm a")
    private Timestamp createdTime;

    private String modifiedBy = "system";
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm a")
    private Timestamp modifiedTime;

    public GenericDetailsDTO(GenericDetailsDTOBuilder genericDetailsBuilder) {
        this.createdBy = genericDetailsBuilder.createdBy;
        this.createdTime = genericDetailsBuilder.createdTime;
        this.modifiedBy = genericDetailsBuilder.modifiedBy;
        this.modifiedTime = genericDetailsBuilder.modifiedTime;
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
    public GenericDetailsDTO() {}

    public String getCreatedBy() {
        return createdBy;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public static class GenericDetailsDTOBuilder{

        private String createdBy = "system";
        @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm a")
        private Timestamp createdTime;

        private String modifiedBy = "system";
        @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm a")
        private Timestamp modifiedTime;

        @PrePersist
        protected void onCreate() {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (createdBy == null) createdBy = "system";
            if (modifiedBy == null) modifiedBy = createdBy;
            createdTime = now;
            modifiedTime = now;
        }

        @PreUpdate
        protected void onUpdate() {
            modifiedTime = Timestamp.valueOf(LocalDateTime.now());
        }

        public GenericDetailsDTOBuilder(){}

        public GenericDetailsDTOBuilder setCreatedBy(String createdBy) {
            this.createdBy = createdBy; return this;
        }
        public GenericDetailsDTOBuilder setCreatedTime(Timestamp createdTime) {
            this.createdTime = createdTime; return this;
        }
        public GenericDetailsDTOBuilder setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy; return this;
        }
        public GenericDetailsDTOBuilder setModifiedTime(Timestamp modifiedTime) {
            this.modifiedTime = modifiedTime; return this;
        }

        public GenericDetailsDTO build(){
            return new GenericDetailsDTO(this);
        }
    }
}

