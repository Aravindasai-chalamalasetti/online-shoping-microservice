package com.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
@Schema(
        name = "RamDetails",
        description = "This is hold Ramdetails information"
)
public class RamDetailsDTO {

    @Schema(
            description = "This is ram id",example = "1"
    )
    private Long ramId;
    @Schema(
            description = "This is ramSize",example = "8 Gb"
    )
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

