package com.example.repository;

import com.example.entity.Details;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IDetailsRepository extends JpaRepository<Details, Integer> {


    // tổng số giờ di chuyển nhiều lộ trình
    @Query(value = "SELECT TIMEDIFF(end_time, start_time) \n" +
            "FROM details\n" +
            "where id_movement_history = :id_movement_history", nativeQuery = true)
    List<String> getListTimeByMovementHistoryId(@Param("id_movement_history") int movementHistoryId);

    // số lần tạm dừng
    @Query(value = "SELECT count(1) \n" +
            "FROM details\n" +
            "where id_movement_history = :id_movement_history", nativeQuery = true)
    int getPauseCountByMovementHistoryId(@Param("id_movement_history") int movementHistoryId);

    // giờ kết thúc
    @Query(value = "SELECT TIME(end_time) FROM details\n" +
            "where id_movement_history = :id_movement_history", nativeQuery = true)
    List<String> getTimeByMovementHistoryId(@Param("id_movement_history") int movementHistoryId);

    // lấy tốc độ trung bình
    @Query(value = "SELECT AVG(average_speed) FROM details\n" +
            "where id_movement_history = :id_movement_history ", nativeQuery = true)
    Float getAVGByMovementHistoryId(@Param("id_movement_history") int movementHistoryId);

    @Query(value = "SELECT * FROM details\n" +
            "where id_movement_history = :id_movement_history " +
            "ORDER BY end_time DESC", nativeQuery = true)
    Page<Details> getByMovementHistoryId(Pageable pageable , @Param("id_movement_history") int movementHistoryId);

    // tổng số giờ di chuyển 1 lộ trình
    @Query(value = "SELECT TIMEDIFF(end_time, start_time) \n" +
            "FROM details\n" +
            "where id = :id", nativeQuery = true)
    String getTimeById(@Param("id") int id);


}
