package com.example.p02solar_park_api.solar_park_api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseUtil {
    public static ResponseEntity<?> respond(String key, Object value, int statusCode) {
        HashMap<String, Object> response = new HashMap<>();
        response.put(key, value);

        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }
}
