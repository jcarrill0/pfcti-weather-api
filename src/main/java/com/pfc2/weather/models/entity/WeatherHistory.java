package com.pfc2.weather.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double lon;
    private Double lat;
    private String weather;
    private Double tempMin;
    private Double tempMax;
    private Double humidity;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime created;
}
