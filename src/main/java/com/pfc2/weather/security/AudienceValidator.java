package com.pfc2.weather.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

@RequiredArgsConstructor(staticName = "of")
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private static final String INVALID_TOKEN_CODE_ERROR = "Invalid_token";
    private static final String INVALID_TOKEN_DESC_CODE_ERROR = "The required audience is missing";

    private final String audience;

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        OAuth2Error error = new OAuth2Error(INVALID_TOKEN_CODE_ERROR,
                                            INVALID_TOKEN_DESC_CODE_ERROR,
                                            null);

        if (jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        }

        return OAuth2TokenValidatorResult.failure(error);
    }
}
