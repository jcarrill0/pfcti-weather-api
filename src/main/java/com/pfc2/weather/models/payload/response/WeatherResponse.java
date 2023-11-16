package com.pfc2.weather.models.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private String weather;
    private Double tempMin;
    private Double tempMax;
    private Double humidity;
}
