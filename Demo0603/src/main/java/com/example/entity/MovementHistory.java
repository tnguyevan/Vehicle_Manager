package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Movement_history")
public class MovementHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreatedDate
    @Column(name = "start_date", insertable = false)
    @CreationTimestamp
    private LocalDateTime startDate;

    @Column(name = "average_speed", nullable = false)
    private Float averageSpeed;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

//    @OneToMany(mappedBy = "movementHistory")
//    private List<Details> details;



}