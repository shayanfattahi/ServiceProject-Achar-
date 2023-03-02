package com.example.upgradeservice.service;

import com.example.upgradeservice.exception.*;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.repository.ClientRepo;
import com.example.upgradeservice.utils.Utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
//
//    @PersistenceContext
//    private EntityManager em;
//    List<Client> getClientByName(String name){
//        Criteria crit = em.unwrap(Session.class).createCriteria(Client.class);
//        crit.add(Restrictions.eq("firstName", name));
//        List<Client> students = crit.list();
//        return students;
//    }
//
//    List<Client> getClientByLastName(String lastname){
//        Criteria crit = em.unwrap(Session.class).createCriteria(Client.class);
//        crit.add(Restrictions.eq("lastName", lastname));
//        List<Client> students = crit.list();
//        return students;
//    }
//
//    Client getClientByEmail(String email){
//        Criteria crit = em.unwrap(Session.class).createCriteria(Client.class);
//        crit.add(Restrictions.eq("lastName", email));
//        List<Client> students = crit.list();
//        return students.get(0);
//    }

    public void create(Client client){
        clientRepo.save(client);
    }

}
