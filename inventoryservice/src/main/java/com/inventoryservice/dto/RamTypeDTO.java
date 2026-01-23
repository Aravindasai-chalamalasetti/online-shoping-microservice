package com.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
@Schema(
        name = "RamType",
        description = "This is hold ramType information"
)
public class RamTypeDTO {
    @Schema(
            description = "This is ramType id",example = "2"
    )
    private Long ramTypeId;
    @Schema(
            description = "This is ramType",example = "DDR1"
    )
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

