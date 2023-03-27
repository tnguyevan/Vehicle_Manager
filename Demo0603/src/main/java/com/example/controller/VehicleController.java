package com.example.controller;


import com.example.form.VehicleFormCreate;
import com.example.service.IVehicleService;
import com.example.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/vehicle")
public class VehicleController {

    @Autowired
    private IVehicleService iVehicleService;


    @GetMapping(value = "/get-list-vehicle")
    public ResponseEntity<?> getListVehicle() {
        return ResponseUtil.ok(iVehicleService.getListVehicleByUsername());
    }

    @PostMapping()
    public ResponseEntity<?> createVehicle(@RequestBody VehicleFormCreate createFormCreate) {

        return ResponseUtil.created(iVehicleService.createVehicle(createFormCreate));


    }
}





