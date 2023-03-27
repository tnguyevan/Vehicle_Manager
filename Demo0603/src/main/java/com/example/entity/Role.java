package com.example.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "`Role`")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "role_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true)
    private ERole eRole = ERole.ENDUSER;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public enum ERole {
        ADMIN, ENDUSER;
    }

}
