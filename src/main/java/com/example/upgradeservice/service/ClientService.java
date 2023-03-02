package com.example.upgradeservice.service;

import com.example.upgradeservice.exception.*;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.repository.ClientRepo;
import com.example.upgradeservice.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepo clientRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientService(ClientRepo clientRepo, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepo = clientRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void createClient(Client client){
        client.setDate(Utils.Date_today);
        if (clientRepo.findClientByEmail(client.getEmail()).isPresent()){
            throw new DuplicateUserException();
        }

        if (!client.getPass().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) {
            throw new InvalidPassException();
        }
        if (!client.getEmail().matches("^(.+)@(.+)$")) {
            throw new InvalidEmailException();
        }
        if (client.getDate() < Utils.Date_today)
        {
            throw new InvalidDateException();
        }
        client.setPass(passwordEncoder.encode(client.getPassword()));
        clientRepo.save(client);
    }

    public Optional<Client> signIn(String email , String pass){
        if (!clientRepo.findClientByEmail(email).isPresent() || !clientRepo.findClientByEmailAndPass(email, pass).isPresent()){
            throw new InvalidEntityException();
        }else
            return clientRepo.findClientByEmailAndPass(email , pass);
    }

    public Client findByEmail(String email){
        return clientRepo.findClientByEmail(email).get();
    }

    public void changePass(String email , String pass , String passNew){
        Optional<Client> client = signIn(email , pass);
        if (passNew.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")){
            client.get().setPass(passNew);
            clientRepo.save(client.get());
        }else {
            throw new InvalidPassException();
        }
    }

    @PersistenceContext
    private EntityManager em;

    @Transactional
    List<Client> getClient(){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> studentRoot = criteriaQuery.from(Client.class);
        criteriaQuery.select(studentRoot);
        TypedQuery<Client> typedQuery = em.createQuery(criteriaQuery);
        List<Client> studentList = typedQuery.getResultList();
        return studentList;
    }

    @Transactional
    List<Client> getClientByEmail(String email){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> studentRoot = criteriaQuery.from(Client.class);
        criteriaQuery.select(studentRoot);
        criteriaQuery.where(criteriaBuilder.equal(studentRoot.get("email"), email ));
        TypedQuery<Client> typedQuery = em.createQuery(criteriaQuery);
        List<Client> studentList = typedQuery.getResultList();
        return studentList;
    }

    public void create(Client client){
        clientRepo.save(client);
    }

}
