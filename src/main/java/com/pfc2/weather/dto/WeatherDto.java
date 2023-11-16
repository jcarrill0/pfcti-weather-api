package com.pfc2.weather.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDto {
    private Long id;
    private String weather;
    private Double tempMin;
    private Double tempMax;
    private Double humidity;
    private LocalDateTime created;
}
