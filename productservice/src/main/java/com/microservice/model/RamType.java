package com.microservice.model;

import com.microservice.sequence.SequenceGeneratorService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ramType-details")
public class RamType {
    @Id
    private Long ramTypeId;
    @NotEmpty(message = "RAM Type can't be empty")
    @Size(min = 3, max = 16, message = "RAM type must be between 3 and 16 characters")
    private String ramType;

    public RamType(Long ramTypeId,String ramType) {
        this.ramTypeId = ramTypeId;
        this.ramType = ramType;
    }
    public void generateIdIfNeeded(SequenceGeneratorService sequenceGenerator) {
        if (this.ramTypeId == null) {
            this.ramTypeId = sequenceGenerator.generateSequence("ramType_details_seq");
        }
    }
    public RamType(){

    }

    public Long getRamTypeId() {
        return ramTypeId;
    }

    public void setRamTypeId(Long ramTypeId) {
        this.ramTypeId = ramTypeId;
    }

    public String getRamType() {
        return ramType;
    }

    public void setRamType(String ramType) {
        this.ramType = ramType;
    }

    @Override
    public String toString() {
        return "RamType{" +
                "ramTypeId=" + ramTypeId +
                ", ramType='" + ramType + '\'' +
                '}';
    }
}
