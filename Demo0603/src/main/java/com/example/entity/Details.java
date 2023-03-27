package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Details")
public class Details implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "start_position", nullable = false, length = 30)
    private String startPosition;

    @Column(name = "end_position", nullable = false, length = 30)
    private String endPosition;

    @Column(name = "average_speed", nullable = false)
    private Float averageSpeed;

    @CreatedDate
    @Column(name = "start_time", insertable = false)
    @CreationTimestamp
    private LocalDateTime startTime;

    @CreatedDate
    @Column(name = "end_time", insertable = false)
    @CreationTimestamp
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "id_movement_history")
    private MovementHistory movementHistory;


}