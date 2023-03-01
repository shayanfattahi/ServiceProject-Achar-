package com.example.upgradeservice.repository;

import com.example.upgradeservice.model.users.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicianRepo extends JpaRepository<Technician , Long> {

    Technician findClientByEmail(String email);

    Optional<Technician> findClientByEmailAndPass(String email , String pass);

    Technician findTechnicianById(Long id);

    @Query(value = "Select * from technician\n" +
            "INNER JOIN technician_under_services t on technician.id = t.technician_id\n" +
            "where t.under_services_id=?1 " , nativeQuery = true)
    List<Technician> TechnicianByUnderService(Long id);
}
