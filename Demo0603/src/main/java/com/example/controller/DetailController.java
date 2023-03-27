package com.example.controller;


import com.example.service.IDetailService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/detail")
public class DetailController {

    @Autowired
    private IDetailService iDetailService;


    @GetMapping(value = "/get-list-detail")
    public ResponseEntity<?> getListDetailByMovementHistoryId(Pageable pageable, @Parameter(name = "movementHistoryId") int movementHistoryId) {

        return ResponseEntity.ok(iDetailService.getListDetailByMovementHistoryId(pageable,movementHistoryId));
    }

}





