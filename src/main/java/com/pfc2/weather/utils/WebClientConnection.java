package com.pfc2.weather.utils;

import com.pfc2.weather.exceptions.BusinessException;
import com.pfc2.weather.global.ErrorCode;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class WebClientConnection {
    private final WebClient webClient;

    public WebClientConnection() {
        this(WebClient.create());
    }

    public WebClientConnection(WebClient webClient) {
        this.webClient = webClient;
    }
    public JSONObject callServiceGet(String url) {
        String webClientResponse = this.webClient.get()
                .uri(URI.create(url))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, error -> Mono.error(badRequestExceptionGenerate(ErrorCode.ERROR_400_DESC)))
                .bodyToMono(String.class)
                .block();

        return new JSONObject(webClientResponse);
    }

    private BusinessException badRequestExceptionGenerate(String message) {
        return new BusinessException(ErrorCode.ERROR_400, HttpStatus.BAD_REQUEST, message);
    }
}
