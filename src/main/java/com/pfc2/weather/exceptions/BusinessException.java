package com.pfc2.weather.exceptions;

import com.pfc2.weather.global.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private String code;
    private HttpStatus status;
    List<String> errorDescription;

    public BusinessException(String code, HttpStatus status, List<String> errorDescription) {
        this.code = code;
        this.status = status;
        this.errorDescription = errorDescription;
    }

    public BusinessException(String code, HttpStatus status, String errorDescription) {
        this.code = code;
        this.status = status;
        this.errorDescription = ErrorCode.errorDescriptioMapping(errorDescription, code);
    }

    public BusinessException(BusinessException bex) {
        this.code = bex.getCode();
        this.status = bex.getStatus();
        this.errorDescription = bex.getErrorDescription();
    }

    public BusinessException() { }
}
