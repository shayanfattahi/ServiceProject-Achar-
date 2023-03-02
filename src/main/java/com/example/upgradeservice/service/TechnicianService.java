package com.example.upgradeservice.service;

import com.example.upgradeservice.exception.*;
import com.example.upgradeservice.model.users.Technician;
import com.example.upgradeservice.repository.TechnicianRepo;
import com.example.upgradeservice.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class TechnicianService {

    private final TechnicianRepo technicianRepo;
    public TechnicianService(TechnicianRepo technicianRepo) {
        this.technicianRepo = technicianRepo;
    }

    public void createTechnician(Technician technician){
        technician.setDate(Utils.Date_today);
        if (technicianRepo.findClientByEmail(technician.getEmail()).isPresent()){
            throw new DuplicateUserException();
        }

        if (!technician.getPass().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) {
            throw new InvalidPassException();
        }
        if (!technician.getEmail().matches("^(.+)@(.+)$")) {
            throw new InvalidEmailException();
        }
        if (technician.getDate() < Utils.Date_today)
        {
            throw new InvalidDateException();
        }
        technicianRepo.save(technician);
    }

    public Optional<Technician> signIn(String email , String pass){
        if (!technicianRepo.findClientByEmail(email).isPresent() || !technicianRepo.findClientByEmailAndPass(email, pass).isPresent()){
            throw new InvalidEntityException();
        }else
            return technicianRepo.findClientByEmailAndPass(email , pass);
    }

    public Technician findByEmail(String email){
        return technicianRepo.findClientByEmail(email).get();
    }

    public void create(Technician technician){
        technicianRepo.save(technician);
    }

    public void changePass(String email , String pass , String passNew){
        Optional<Technician> te = signIn(email , pass);
        if (passNew.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")){
            te.get().setPass(passNew);
            technicianRepo.save(te.get());
        }else {
            throw new InvalidPassException();
        }
    }

    public void updateTechnician(Technician technician){
        technicianRepo.save(technician);
    }

    public void delete(Technician technician){
        technicianRepo.delete(technician);
    }

    public Technician readById(Long id){
        return technicianRepo.findTechnicianById(id);
    }

//    @PersistenceContext
//    private EntityManager em;
//    List<Technician> getTechnicianByName(String name){
//        Criteria crit = em.unwrap(Session.class).createCriteria(Technician.class);
//        crit.add(Restrictions.eq("firstName", name));
//        List<Technician> students = crit.list();
//        return students;
//    }
//
//    List<Technician> getTechnicianByLastName(String lastname){
//        Criteria crit = em.unwrap(Session.class).createCriteria(Technician.class);
//        crit.add(Restrictions.eq("lastName", lastname));
//        List<Technician> students = crit.list();
//        return students;
//    }
//
//    Technician getTechnicianByEmail(String email){
//        Criteria crit = em.unwrap(Session.class).createCriteria(Technician.class);
//        crit.add(Restrictions.eq("lastName", email));
//        List<Technician> students = crit.list();
//        return students.get(0);
//    }
//
//    @Transactional
//    public List<Technician> getTechnicianByPoint(){
//        Session session = em.unwrap(Session.class);
//        Criteria criteria = session.createCriteria(Technician.class);
//        criteria.addOrder(Order.asc("point"));
//        List<Technician> studentList = criteria.list();
//        return studentList;
//    }
//
//    public List<Technician> getTechnicianByUnderService(Long id){
//        Criteria crit = em.unwrap(Session.class).createCriteria(Technician.class);
//        crit.add(Restrictions.eq("underServices.id", id));
//        List<Technician> students = crit.list();
//        return students;
//    }

    public List<Technician> getTechByUnderService(Long id){
        return technicianRepo.TechnicianByUnderService(id);
    }


}
