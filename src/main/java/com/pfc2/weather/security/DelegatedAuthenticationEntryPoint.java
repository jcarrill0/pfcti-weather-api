package com.pfc2.weather.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfc2.weather.dto.ErrorDto;
import com.pfc2.weather.global.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.io.OutputStream;

@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        ErrorDto error = ErrorDto.builder()
                .code(ErrorCode.ERROR_401)
                .errors(ErrorCode.errorDescriptioMapping(ErrorCode.ERROR_401_DESC, ErrorCode.ERROR_401))
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, error);
        responseStream.flush();
    }
}
