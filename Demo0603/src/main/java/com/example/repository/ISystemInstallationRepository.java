package com.example.repository;

import com.example.entity.SystemInstallation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ISystemInstallationRepository extends JpaRepository<SystemInstallation, Integer> {

}
