package com.example.repository;

import com.example.entity.token.RegistrationUserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface RegistrationUserTokenRepository extends JpaRepository<RegistrationUserToken, Integer> {

    RegistrationUserToken findByToken(int token);

    boolean existsByToken(int token);

    @Transactional
    @Modifying
    void deleteByToken(int token);

    @Query("	SELECT 	token	"
            + "	FROM 	RegistrationUserToken "
            + " WHERE 	user.id = :userId")
    String findByUserId(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("	DELETE 							"
            + "	FROM 	RegistrationUserToken 	"
            + " WHERE 	user.id = :userId")
    void deleteByUserId(@Param("userId") int userId);

}
