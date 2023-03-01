package com.example.upgradeservice.repository;

import com.example.upgradeservice.model.services.UnderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnderServicesRepo extends JpaRepository<UnderService , Long> {

    List<UnderService> readUnderServiceByServicesId(Long id);

    UnderService readUnderServiceById(Long id);

    UnderService readUnderServiceByName(String name);
}
