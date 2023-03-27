package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "system_installation")
public class SystemInstallation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hotline", nullable = false, length = 200)
    private String hotline;

    @Column(name = "banner", nullable = false, length = 200)
    private String banner;

    @Column(name = "logo", nullable = false, length = 200)
    private String logo;

}