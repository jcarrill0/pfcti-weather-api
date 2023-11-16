package com.pfc2.weather.controllers;

import com.pfc2.weather.dto.ErrorDto;
import com.pfc2.weather.exceptions.BusinessException;
import com.pfc2.weather.models.entity.WeatherHistory;
import com.pfc2.weather.models.payload.request.WeatherRequest;
import com.pfc2.weather.models.payload.response.WeatherResponse;
import com.pfc2.weather.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Tag(name = "WeatherApiController")
@RestController
@RequestMapping(value="/api/v1/weather", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @Operation(summary = "Obtiene las condiciones del clima por lon y lat",
            description = "Introduce las coordenas (lat=latitud, lon=longitud) para obtener las condiciones del clima")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = WeatherResponse.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",
                    content = {@Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<?> currentWeather(@RequestBody WeatherRequest weatherRequest) throws Exception {
       try{
           log.info("Request recibido", weatherRequest);
           WeatherResponse res = weatherService.getCurrentWeather(weatherRequest);
           return ResponseEntity.ok(res);
       } catch (BusinessException bex ){
           log.error("Error de negocio", bex);
            throw new BusinessException(bex);
       } catch (Exception ex){
           log.error("Error inesperado", ex);
            throw new Exception(ex);
       }
    }

    @Operation(summary = "Obtiene el historial del clima",
            description = "Devuelve el historial del clima en base datos")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(
                                array = @ArraySchema(schema = @Schema(implementation =  WeatherHistory.class)),
                                mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    content = {@Content(
                                schema = @Schema(implementation = ErrorDto.class),
                                mediaType = "application/json")}),
            @ApiResponse(responseCode = "401",
                    content = {@Content(
                            schema = @Schema(implementation = ErrorDto.class),
                            mediaType = "application/json")})
    })
    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getHistory() throws Exception {
        try {
            Map<String, Object> res = weatherService.getListWeathers();
            return ResponseEntity.ok(res);
        } catch (Exception ex){
            log.error("Error inesperado", ex);
            throw new Exception(ex);
        }


    }
}
