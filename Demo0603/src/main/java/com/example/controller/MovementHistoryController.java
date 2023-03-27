package com.example.controller;


import com.example.service.IMovementHistoryService;
import com.example.service.IVehicleService;
import com.example.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/movementHistory")
public class MovementHistoryController {

    @Autowired
    private IMovementHistoryService iMovementHistoryService;


    @GetMapping(value = "/get-movementHistory")
    public ResponseEntity<?> getMovementHistoryByVehicleId(@Parameter(name = "vehicleId") int vehicleId) {
        return ResponseUtil.ok(iMovementHistoryService.getMovementHistoryByVehicleId(vehicleId));
    }

}





