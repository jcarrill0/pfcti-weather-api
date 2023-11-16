package com.pfc2.weather.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDto {
 private String code;
 private List<String> errors;
}
