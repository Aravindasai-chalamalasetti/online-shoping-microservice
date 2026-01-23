package com.microservice.dto;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionDTO extends RuntimeException{
    private int customCode;
    private String customMessage;
    private Object customBody;
    private HttpStatus status;
    private Date date;

    public ExceptionDTO(String customMessage, Date date, HttpStatus status, Object customBody) {
        super(customMessage);
        this.customMessage = customMessage;
        this.date = date;
        this.status = status;
        this.customBody = customBody;
    }
    public ExceptionDTO(String message, int customCode, String customMessage, Object customBody, HttpStatus status, Date date) {
        super(message);
        this.customCode = customCode;
        this.customMessage = customMessage;
        this.customBody = customBody;
        this.status = status;
        this.date = date;
    }

    public ExceptionDTO() {

    }

    public int getCustomCode() {
        return customCode;
    }

    public void setCustomCode(int customCode) {
        this.customCode = customCode;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public Object getCustomBody() {
        return customBody;
    }

    public void setCustomBody(Object customBody) {
        this.customBody = customBody;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
