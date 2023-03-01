package com.example.upgradeservice.repository;

import com.example.upgradeservice.model.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client , Long> {

    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientByEmailAndPass(String email , String pass);


}
