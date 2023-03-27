package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "vehicle_name", nullable = false, length = 30)
    private String vehicleName;

    @Column(name = "license_plates", nullable = false, length = 15)
    private String licensePlates;

    @Column(name = "vehicle_description", nullable = false, length = 200)
    private String vehicleDescription;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "`status`", nullable = false)
    private VehicleStatus status = VehicleStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MovementHistory> movementHistoryList;
}