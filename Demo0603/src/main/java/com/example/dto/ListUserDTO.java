package com.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUserDTO {

    private int id;

    private String name;

    private String phoneNumber;

    private String address;
}
