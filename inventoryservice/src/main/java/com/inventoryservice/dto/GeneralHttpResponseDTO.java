package com.inventoryservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Schema(
        name = "GeneralHttpResponse",
        description = "This GeneralHttpResponse is used for handling both success and failure case issues"
)
public class GeneralHttpResponseDTO<T> {

    @Schema(
            description = "Display dynamic custom responseCode"
    )
    private int responseCode;

    @Schema(
            description = "Display dynamic custom responseMessage",example = "responseMessage field is not empty"
    )
    private String responseMessage;

    @Schema(
            description = "Display dynamic custom responseBody"
    )
    private T responseBody;

    @Schema(
            description = "Display dynamic current date",example = "date format will be 2025-12-25 04:00 PM"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm a")
    private Date date;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

