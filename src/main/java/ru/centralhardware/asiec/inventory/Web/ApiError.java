package ru.centralhardware.asiec.inventory.Web;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiError {

    private int code;
    private String message;
    private Instant timestamp;
    private String path;

    public ApiError(int code, String message, String path) {
        this(code, message, path, Instant.now());

    }

    public ApiError(int code, String message, String path,  Instant timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

}
