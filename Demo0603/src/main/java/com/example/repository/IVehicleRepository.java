package com.example.repository;

import com.example.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Integer> {


    @Query(value = "SELECT * FROM vehicle v\n" +
            "join `user` u\n" +
            "using (user_id)\n" +
            "join movement_history m \n" +
            "on m.vehicle_id = v.id\n" +
            "where u.user_name = :user_name and DATE(m.start_date) = :start_date", nativeQuery = true)
    List<Vehicle> findByUsernameAndStartDate(@Param("user_name") String username, @Param("start_date") String date);



}
