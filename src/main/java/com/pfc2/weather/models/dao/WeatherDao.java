package com.pfc2.weather.models.dao;

import com.pfc2.weather.models.entity.WeatherHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherDao extends JpaRepository<WeatherHistory, Long> {
    Optional<WeatherHistory> findByLatAndLon(Double lon, Double lat);
}
