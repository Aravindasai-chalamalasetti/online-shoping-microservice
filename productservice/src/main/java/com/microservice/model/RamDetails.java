package com.microservice.model;


import com.microservice.sequence.SequenceGeneratorService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ram-details")
public class RamDetails {
    @Id
    private Long ramId;  // Auto-generated manually via sequence generator

    @NotEmpty(message = "RAM Size can't be empty")
    @Size(min = 3, max = 10, message = "RAM size must be between 3 and 10 characters")
    private String ramSize;   // e.g., "4 GB", "8 GB", "16 GB"

    // Constructors
    public RamDetails() {}
    public RamDetails(Long ramId,String ramSize) {
        this.ramId = ramId;
        this.ramSize = ramSize;
    }
    public void generateIdIfNeeded(SequenceGeneratorService sequenceGenerator) {
        if (this.ramId == null) {
            this.ramId = sequenceGenerator.generateSequence("ram_details_seq");
        }
    }
    // Getters and Setters
    public Long getRamId() { return ramId; }
    public void setRamId(Long ramId) { this.ramId = ramId; }
    public String getRamSize() { return ramSize; }
    public void setRamSize(String ramSize) { this.ramSize = ramSize; }
}
