package com.example.repository;

import com.example.entity.MovementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IMovementHistoryRepository extends JpaRepository<MovementHistory, Integer> {



    @Query(value = "SELECT * FROM movement_history m\n" +
            "where m.vehicle_id = :vehicle_id and DATE(m.start_date) = :start_date", nativeQuery = true)
    MovementHistory getByVehicleIdAndDate(@Param("vehicle_id") int vehicleId, @Param("start_date") String date);


    @Query(value = "SELECT * FROM movement_history m\n" +
            "join vehicle v\n" +
            "on m.vehicle_id = v.id\n" +
            "join `user` u \n" +
            "on u.user_id = v.user_id\n" +
            "where u.user_name = :user_name and m.vehicle_id = :vehicle_id " +
            "ORDER BY DATE(m.start_date) DESC", nativeQuery = true)
    List<MovementHistory> getByUsernameAndVehicleId(@Param("user_name") String username, @Param("vehicle_id") int vehicleId);

}
