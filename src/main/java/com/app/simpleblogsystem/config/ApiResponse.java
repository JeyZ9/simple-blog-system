package com.app.simpleblogsystem.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private final Integer status;
    private final String message;
    private final T data;

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
