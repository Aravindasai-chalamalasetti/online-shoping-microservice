package com.microservice.model;

public class DatabaseSequence {
    private String id;
    private long sequenceValue;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public long getSequenceValue() { return sequenceValue; }
    public void setSequenceValue(long sequenceValue) { this.sequenceValue = sequenceValue; }
}
