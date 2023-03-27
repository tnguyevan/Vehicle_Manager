package com.example.service;

import com.example.dto.MovementHistoryDTO;

import java.text.ParseException;
import java.util.List;


public interface IMovementHistoryService {

    List<MovementHistoryDTO> getMovementHistoryByVehicleId(int vehicleId);


}
