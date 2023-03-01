package com.example.upgradeservice.repository;

import com.example.upgradeservice.model.PhotoTec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoTecRepo extends JpaRepository<PhotoTec , Long> {

}
