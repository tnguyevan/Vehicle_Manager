package com.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementHistoryDTO {

    private int id;

    private float avgSpeed;

    private int pauseCount;

    private String startTime;

    private String startDate;

    private String TotalTravelTime;

    private String endTime;
}
