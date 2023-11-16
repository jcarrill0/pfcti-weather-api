package com.pfc2.weather.exceptions;

import com.pfc2.weather.dto.ErrorDto;
import com.pfc2.weather.global.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDto> runtimeExceptionHandler(BusinessException ex) {
        ErrorDto error = ErrorDto.builder()
                                .code(ex.getCode())
                                .errors(ex.getErrorDescription())
                                .build();

        return new ResponseEntity<>(error, ex.getStatus());
    }

    //controla los errores autorizacion y autenticacion
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(Exception ex) {
        ErrorDto error = ErrorDto.builder()
                .code(ErrorCode.ERROR_401)
                .errors(ErrorCode.errorDescriptioMapping(ErrorCode.ERROR_401_DESC, ErrorCode.ERROR_401))
                .build();

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    //controla los errores de varios tipos y globalizrlo con un error 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handlerException(Exception ex) {
        ErrorDto error = ErrorDto.builder()
                .code(ErrorCode.ERROR_503)
                .errors(ErrorCode.errorDescriptioMapping(ex.getMessage(), ErrorCode.ERROR_503))
                .build();

        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
