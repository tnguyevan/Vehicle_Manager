package com.example.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<?> ok(T data) {
        ServiceResult<T> serviceResult = new ServiceResult<>(HttpStatus.OK, data);
        return ResponseEntity.status(HttpStatus.OK).body(serviceResult);
    }

    public static <T> ResponseEntity<?> ok(T data, String message){
        ServiceResult<T> serviceResult = new ServiceResult<>(HttpStatus.OK, message, data);
        return ResponseEntity.status(HttpStatus.OK).body(serviceResult);
    }
    public static <T> ResponseEntity<?> created(T data){
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    public static <T> ResponseEntity<?> ok(String message) {
        ServiceResult<T> serviceResult = new ServiceResult<>(HttpStatus.OK, message);
        return ResponseEntity.status(HttpStatus.OK).body(serviceResult);
    }

    public static <T> ResponseEntity<?> badRequest(String message) {
        ServiceResult<T> serviceResult = new ServiceResult<>(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResult);
    }

}
