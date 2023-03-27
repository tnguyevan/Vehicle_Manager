package com.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailDTO {

    private String startPosition;

    private String endPosition;

    private Float averageSpeed;

    private String startTime;

    private String startDate;

    private String TotalTravelTime;

    private String endTime;
}
