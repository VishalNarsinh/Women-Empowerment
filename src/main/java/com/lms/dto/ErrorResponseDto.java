package com.lms.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {
    public ErrorResponseDto(String apiPath, HttpStatus errorCodeString, int errorCode, String errorMessage, LocalDateTime errorTime) {
        this.apiPath = apiPath;
        this.errorCodeString = errorCodeString;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorTime = errorTime;
    }

    private  String apiPath;

    private HttpStatus errorCodeString;

    private int errorCode;

    private  String errorMessage;

    private LocalDateTime errorTime;


}
