package com.example.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleFormCreate {

    private String vehicleName;

    private String licensePlates;

    private String vehicleDescription;

    private int userId;


}
