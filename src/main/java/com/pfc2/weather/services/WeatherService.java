package com.pfc2.weather.services;

import com.pfc2.weather.exceptions.BusinessException;
import com.pfc2.weather.global.ErrorCode;
import com.pfc2.weather.models.dao.WeatherDao;
import com.pfc2.weather.models.entity.WeatherHistory;
import com.pfc2.weather.models.payload.request.WeatherRequest;
import com.pfc2.weather.models.payload.response.WeatherResponse;
import com.pfc2.weather.utils.WebClientConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherService {
    @Autowired
    WebClientConnection webClientConnect;

    @Autowired
    private WeatherDao weatherRepository;

    private String APIkey = "b15dd521bf76013ba7991a326867e1b5";
    private String baseUrl = "https://api.openweathermap.org/data/2.5/weather?lat=";

    public WeatherResponse getCurrentWeather(WeatherRequest weatherRequest) {
        Double lat = weatherRequest.getLat();
        Double lon = weatherRequest.getLon();
        List<String> errorDescription  = new ArrayList<>();

        if(lat == null || lon == null) {
            throw new BusinessException(ErrorCode.ERROR_400, HttpStatus.BAD_REQUEST, ErrorCode.ERROR_400_DESC);
        }

        // Buscamos en la db si existe el clima con los parametros
        Optional<WeatherHistory> weather = findWeatherByLatAndLon(lat, lon);

        // si no obtenemos resultados obtenemos la info desde el api service
        if(weather.isEmpty()){
            return getWeatherResponseFromApi(lat, lon);
        }

        WeatherHistory weatherHistory = weather.get();

        // Si el tiempo es mayor a 10 minutos obtenemos la info desde el api service y actualizamos la db
        if(getDifferenceBetweenDates(weatherHistory.getCreated()) > 10){
            return updateWeather(weatherHistory);
        }

        // devolvemos info desde la db
        return buildWeatherResponseFromDB(weatherHistory);
    }

    public Map<String, Object> getListWeathers() {
        Map<String, Object> data = new HashMap<>();

        data.put("weatherHistory", getAllWeathers());

        return data;
    }

    private WeatherResponse getWeatherResponseFromApi(Double lat, Double lon) {
        WeatherResponse weatherResponse;
        // Llamado a api
        JSONObject jsonResponse = webClientConnect.callServiceGet(getUrl(lat, lon));
        JSONObject weatherJson = getWeatherObj(jsonResponse.getJSONArray("weather"));
        JSONObject mainJson = jsonResponse.getJSONObject("main");

        weatherResponse = buildWeatherResponseFromApi(weatherJson, mainJson);

        if(weatherResponse != null) {
            save(mapToWeatherHistory(lat, lon, weatherResponse));
        }

        return weatherResponse;
    }

    private WeatherResponse buildWeatherResponseFromApi(JSONObject weatherJson, JSONObject mainJson) {
        WeatherResponse response = null;

        if(!weatherJson.isEmpty() && !mainJson.isEmpty()) {
            response = new WeatherResponse();
            response.setWeather(weatherJson.getString("main"));
            response.setTempMin(mainJson.getDouble("temp_min"));
            response.setTempMax(mainJson.getDouble("temp_max"));
            response.setHumidity(mainJson.getDouble("humidity"));
        }

        return response;
    }

    private WeatherResponse buildWeatherResponseFromDB(WeatherHistory weatherHistory) {
        WeatherResponse response =  new WeatherResponse();
        response.setWeather(weatherHistory.getWeather());
        response.setTempMin(weatherHistory.getTempMin());
        response.setTempMax(weatherHistory.getTempMax());
        response.setHumidity(weatherHistory.getHumidity());

        return response;
    }

    private WeatherResponse updateWeather(WeatherHistory weatherHistory) {
        // Llamado a api
        JSONObject jsonResponse = webClientConnect.callServiceGet(getUrl(weatherHistory.getLat(), weatherHistory.getLon()));
        JSONObject weatherJson = getWeatherObj(jsonResponse.getJSONArray("weather"));
        JSONObject mainJson = jsonResponse.getJSONObject("main");

        weatherHistory.setWeather(weatherJson.getString("main"));
        weatherHistory.setTempMin(mainJson.getDouble("temp_min"));
        weatherHistory.setTempMax(mainJson.getDouble("temp_max"));
        weatherHistory.setHumidity(mainJson.getDouble("humidity"));
        weatherHistory.setCreated(LocalDateTime.now());

        return buildWeatherResponseFromDB(save(weatherHistory));
    }

    private WeatherHistory mapToWeatherHistory(Double lat, Double lon, WeatherResponse weatherResponse) {
        return WeatherHistory.builder()
                .lat(lat)
                .lon(lon)
                .weather(weatherResponse.getWeather())
                .tempMin(weatherResponse.getTempMin())
                .tempMax(weatherResponse.getTempMax())
                .humidity(weatherResponse.getHumidity())
                .created(LocalDateTime.now())
                .build();
    }

    private WeatherHistory save(WeatherHistory weather) {
        return weatherRepository.save(weather);
    }

    private Optional<WeatherHistory> findWeatherByLatAndLon(Double lat, Double lon) {
        return weatherRepository.findByLatAndLon(lat, lon);
    }

    private List<WeatherHistory> getAllWeathers() {
        return weatherRepository.findAll();
    }

    private JSONObject getWeatherObj(JSONArray weatherJsonArray) {
        return weatherJsonArray.getJSONObject(0);
    }

    private static Long getDifferenceBetweenDates(LocalDateTime startDate) {
        Duration duration = Duration.between(LocalDateTime.now(), startDate);
        return Math.abs(duration.toMinutes());
    }

    private String getUrl(Double lat, Double lon) {
        return baseUrl +lat+"&lon="+lon+"&appid="+APIkey;
    }

}
