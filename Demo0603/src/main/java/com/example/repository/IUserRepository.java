package com.example.repository;

import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {


    User findByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPassword(String password);

    User findUserByPhoneNumber(String phoneNumber);


    @Query(value = "SELECT * FROM `user` u\n" +
            "JOIN `role` r\n" +
            "USING(role_id)\n" +
            "WHERE r.role_name = :roleName", nativeQuery = true)
    Page<User> getAllByRole(Pageable pageable, @Param("roleName") String role);
}
