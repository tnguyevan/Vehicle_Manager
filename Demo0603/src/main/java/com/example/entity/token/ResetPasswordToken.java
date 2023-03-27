package com.example.entity.token;

import com.example.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "`Reset_Password_Token`")
public class ResetPasswordToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", unique = true, nullable = false)
    private int id;

    @Column(name = "`token`", nullable = false, length = 36, unique = true)
    private Integer token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "`expiryDate`", nullable = false)
    private Date expiryDate;

    public ResetPasswordToken() {
    }

    public ResetPasswordToken(int token, User user) {
        this.token = token;
        this.user = user;

        // 1p
        expiryDate = new Date(System.currentTimeMillis() + 60);
    }

}
