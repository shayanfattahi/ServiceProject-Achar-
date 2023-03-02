package com.example.upgradeservice.service;

import com.example.upgradeservice.exception.InvalidDateException;
import com.example.upgradeservice.exception.InvalidEntityException;
import com.example.upgradeservice.exception.InvalidOutPutException;
import com.example.upgradeservice.model.Offered;
import com.example.upgradeservice.model.order.Ordered;
import com.example.upgradeservice.model.order.OrderedStatus;
import com.example.upgradeservice.model.services.UnderService;
import com.example.upgradeservice.repository.OfferedRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferedService {
    final OfferedRepo offeredRepo;
    final ReportService reportService;
    final SubjobService subjobService;

    public OfferedService(OfferedRepo offeredRepo, ReportService reportService, SubjobService subjobService) {
        this.offeredRepo = offeredRepo;
        this.reportService = reportService;
        this.subjobService = subjobService;
    }

    public Offered readById(Long id){
        return offeredRepo.readOfferedById(id);
    }

    public List<Offered> readLogInClientOffered(long id1 , long id2){
        return offeredRepo.readOfferedByClientIdAndOrderedId(id1 , id2);
    }

    public void creatOffered(Offered offered){
        if (offered.getDate() < offered.getOrdered().getDate()){
            throw new InvalidDateException();
        }
        if (!offered.getOrdered().getOrderedStatus().equals(OrderedStatus.WAITINGFOROFFERED)){
            throw new InvalidEntityException();
        }
        offered.setAccepted(false);
        offeredRepo.save(offered);
    }

    public List<Offered> readOfferedSortByPrice(Long id , Long id2){
        return offeredRepo.findOfferedByClientIdAndOrderedIdOrderByPriceAsc(id , id2);
    }

    public List<Offered> readTopTechnician(Long id){
        return offeredRepo.readGoodPoint(id);
    }

    public void acceptOffered(Long offeredChoosen){
        Offered offered = offeredRepo.readOfferedById(offeredChoosen);
        if (offered != null) {
            Optional<Ordered> ordered = reportService.readById(offered.getOrdered().getId());
            if (ordered.get().getOrderedStatus().equals(OrderedStatus.WAITINGFOROFFERED)) {
                ordered.get().setPrice(offered.getPrice());
                ordered.get().setDate(offered.getDate());
                ordered.get().setOrderedStatus(OrderedStatus.WAITINGFORCOMING);
                ordered.get().setTechnician(offered.getTechnician());
                ordered.get().setTime(offered.getTime());
                offered.setAccepted(true);
                offeredRepo.save(offered);
                reportService.createOrderByTechnician(ordered.get());
            }else {
                throw new InvalidEntityException();
            }
        }else
            throw new InvalidOutPutException();

    }

    @PersistenceContext
    private EntityManager em;

    @Transactional
    List<Offered> getOfferedByUnderService(String service){
        UnderService underService = subjobService.readByName(service);
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Offered> criteriaQuery = criteriaBuilder.createQuery(Offered.class);
        Root<Offered> studentRoot = criteriaQuery.from(Offered.class);
        criteriaQuery.select(studentRoot);
        criteriaQuery.where(criteriaBuilder.equal(studentRoot.get("underService"), underService ));
        TypedQuery<Offered> typedQuery = em.createQuery(criteriaQuery);
        List<Offered> studentList = typedQuery.getResultList();
        return studentList;
    }

    @Transactional
    List<Offered> getOfferedByStatus(boolean accepted){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Offered> criteriaQuery = criteriaBuilder.createQuery(Offered.class);
        Root<Offered> studentRoot = criteriaQuery.from(Offered.class);
        criteriaQuery.select(studentRoot);
        criteriaQuery.where(criteriaBuilder.equal(studentRoot.get("accepted"), accepted ));
        TypedQuery<Offered> typedQuery = em.createQuery(criteriaQuery);
        List<Offered> studentList = typedQuery.getResultList();
        return studentList;
    }

    public List<Offered> getOfferedByService(Long service){
        return offeredRepo.readOfferedByService(service);
    }

    public List<Offered> getOfferedByDate(){
        return offeredRepo.readOfferedBySpecialDate();
    }

    public int findCountOfTechnician(Long id){
        return offeredRepo.findCountOfTechnician(id);
    }

}
