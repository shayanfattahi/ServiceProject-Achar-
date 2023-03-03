package com.example.upgradeservice.service;

import com.example.upgradeservice.exception.*;
import com.example.upgradeservice.model.Role;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.model.users.Technician;
import com.example.upgradeservice.repository.TechnicianRepo;
import com.example.upgradeservice.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TechnicianService {

    private final TechnicianRepo technicianRepo;
    private final BCryptPasswordEncoder passwordEncoder;


    public TechnicianService(TechnicianRepo technicianRepo, BCryptPasswordEncoder passwordEncoder) {
        this.technicianRepo = technicianRepo;
        this.passwordEncoder = passwordEncoder;
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
        technician.setPass(passwordEncoder.encode(technician.getPassword()));
        technician.setRole(Role.ROLE_TECHNICIAN);
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

//    public List<Ordered> getOrderedOfTechnician(Long id){
//        return reportService.readOrderOfTechnician(id);
//    }

    @PersistenceContext
    private EntityManager em;
    @Transactional
    List<Technician> getTechnicianByPoint(){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Technician> criteriaQuery = criteriaBuilder.createQuery(Technician.class);
        Root<Technician> studentRoot = criteriaQuery.from(Technician.class);
        criteriaQuery.select(studentRoot);
//        List<Order> orderList = new ArrayList();
//        orderList.add(criteriaBuilder.desc(studentRoot.get("point")));
//        return criteriaQuery.orderBy(orderList);
        criteriaQuery.select(studentRoot).orderBy(criteriaBuilder.asc(studentRoot.get("point")));
        TypedQuery<Technician> typedQuery = em.createQuery(criteriaQuery);
        List<Technician> studentList = typedQuery.getResultList();
        return studentList;
    }
}
