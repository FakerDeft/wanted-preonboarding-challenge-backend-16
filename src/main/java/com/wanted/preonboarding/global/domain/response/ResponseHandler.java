package com.wanted.preonboarding.global.domain.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ResponseHandler<T>(
        HttpStatus statusCode,
        String message,
        T data
) {
}
