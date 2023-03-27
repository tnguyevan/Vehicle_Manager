package com.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListVehicleDTO {

    private int userId;

    private String userName;

    private String userPhoneNumber;

    private String userAddress;

    private int id;

    private String vehicleName;

    private String licensePlates;

    private String vehicleDescription;


}
