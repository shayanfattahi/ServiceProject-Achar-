package com.example.upgradeservice.repository;

import com.example.upgradeservice.model.Manager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepo extends JpaRepository<Manager , Long >{

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM technician_under_services WHERE technician_id =?1 and under_services_id =?2" , nativeQuery = true)
    void deleteUnderserviceAndTech(Long id_tech , Long id_under);

    Optional<Manager> findManagerByUsername(String username);


}
