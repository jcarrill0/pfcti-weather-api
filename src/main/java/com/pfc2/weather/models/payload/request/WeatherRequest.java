package com.pfc2.weather.models.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class WeatherRequest {
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
}
