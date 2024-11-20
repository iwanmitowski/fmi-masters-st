package com.example.p1_rent_a_car.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class AppResponse {
    private static HashMap<String, Object> response = new HashMap<>();

    public static AppResponse success() {
        response = new HashMap<>();
        response.put("status", "success");
        response.put("code", HttpStatus.OK.value());

        return new AppResponse();
    }

    public static AppResponse error() {
        response = new HashMap<>();
        response.put("status", "error");
        response.put("code", HttpStatus.BAD_REQUEST.value());

        return new AppResponse();
    }

    public AppResponse withCode(HttpStatus code){
        response.put("code", code.value());
        return this;
    }

    public AppResponse withMessage(String message){
        response.put("message", message);
        return this;
    }

    public AppResponse withData(Object obj) {
        response.put("data", obj);
        return this;
    }

    public ResponseEntity<?> build() {
        return new ResponseEntity<>(response, HttpStatus.valueOf((int)response.get("code")));
    }
}
