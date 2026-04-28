package com.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class GenericDetailsDTO {

    private String createdBy = "system";
    private String modifiedBy = "system";

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a",timezone = "Asia/Kolkata")
    private Timestamp createdTime;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a",timezone = "Asia/Kolkata")
    private Timestamp modifiedTime;

    public GenericDetailsDTO() {}

    public GenericDetailsDTO(GenericDetailsDTOBuilder builder) {
        this.createdBy = builder.createdBy;
        this.createdTime = builder.createdTime;
        this.modifiedBy = builder.modifiedBy;
        this.modifiedTime = builder.modifiedTime;
    }

    public String getCreatedBy() { return createdBy; }
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    public Timestamp getCreatedTime() { return createdTime; }
    public String getModifiedBy() { return modifiedBy; }
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    public Timestamp getModifiedTime() { return modifiedTime; }

    public static class GenericDetailsDTOBuilder {
        private String createdBy = "system";
        private String modifiedBy = "system";
        @JsonFormat(pattern = "dd-MM-yyyy hh:mm a",timezone = "Asia/Kolkata")
        private Timestamp createdTime;
        @JsonFormat(pattern = "dd-MM-yyyy hh:mm a",timezone = "Asia/Kolkata")
        private Timestamp modifiedTime;

        public GenericDetailsDTOBuilder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public GenericDetailsDTOBuilder setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }
        @JsonFormat
        public GenericDetailsDTOBuilder setCreatedTime(Timestamp createdTime) {
            this.createdTime = createdTime;
            return this;
        }
        @JsonFormat
        public GenericDetailsDTOBuilder setModifiedTime(Timestamp modifiedTime) {
            this.modifiedTime = modifiedTime;
            return this;
        }

        public GenericDetailsDTO build() {
            return new GenericDetailsDTO(this);
        }
    }
}
