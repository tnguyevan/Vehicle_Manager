package com.example.service;

import com.example.dto.ListVehicleDTO;
import com.example.dto.VehicleDTO;
import com.example.entity.Vehicle;
import com.example.form.VehicleFormCreate;

import java.text.ParseException;
import java.util.List;


public interface IVehicleService {

    List<VehicleDTO> getListVehicleByUsername() ;

    ListVehicleDTO createVehicle(VehicleFormCreate vehicleFormCreate);



}
