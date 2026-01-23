package com.microservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class RamTypeDTO {
    private Long ramTypeId;
    @NotEmpty(message = "RAM Type can't be empty")
    @Size(min = 3, max = 16, message = "RAM type must be between 3 and 16 characters")
    private String ramType;

    public RamTypeDTO(RamTypeDTOBuilder ramTypeDTOBuilder) {
        this.ramTypeId = ramTypeDTOBuilder.ramTypeId;
        this.ramType = ramTypeDTOBuilder.ramType;
    }

    public RamTypeDTO(){}

    public Long getRamTypeId() {
        return ramTypeId;
    }

    public String getRamType() {
        return ramType;
    }

    public static class RamTypeDTOBuilder{
        private Long ramTypeId;
        @NotEmpty(message = "RAM Type can't be empty")
        @Size(min = 3, max = 16, message = "RAM type must be between 3 and 16 characters")
        private String ramType;

        public RamTypeDTOBuilder(){}

        public RamTypeDTOBuilder setRamTypeId(Long ramTypeId) {
            this.ramTypeId = ramTypeId;return this;
        }

        public RamTypeDTOBuilder setRamType(String ramType) {
            this.ramType = ramType; return this;
        }

        public RamTypeDTO build(){return  new RamTypeDTO(this);}
    }
}
