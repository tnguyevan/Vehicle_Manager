package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    private int id;

    private String vehicleName;

    private String licensePlates;

    private int pauseCount;

    private String startTime;

    private String TotalTravelTime;

    private String endTime;

}
