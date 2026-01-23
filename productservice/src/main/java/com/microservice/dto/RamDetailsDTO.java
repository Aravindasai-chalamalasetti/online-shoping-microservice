package com.microservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class RamDetailsDTO {

    private Long ramId;

    @NotEmpty(message = "RAM Size can't be empty")
    @Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
    private String ramSize;

    public RamDetailsDTO(RamDetailsDTOBuilder ramDetailsDTOBuilder) {
        this.ramId = ramDetailsDTOBuilder.ramId;
        this.ramSize = ramDetailsDTOBuilder.ramSize;
    }

    public RamDetailsDTO(){}

    public Long getRamId() {
        return ramId;
    }

    public String getRamSize() {
        return ramSize;
    }

    public static class RamDetailsDTOBuilder{
        private Long ramId;

        @NotEmpty(message = "RAM Size can't be empty")
        @Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
        private String ramSize;

        public RamDetailsDTOBuilder(){}

        public RamDetailsDTOBuilder setRamId(Long ramId) {
            this.ramId = ramId;
            return this;
        }

        public RamDetailsDTOBuilder setRamSize(String ramSize) {
            this.ramSize = ramSize;  return this;
        }

        public RamDetailsDTO build(){ return new RamDetailsDTO(this);}
    }
}
