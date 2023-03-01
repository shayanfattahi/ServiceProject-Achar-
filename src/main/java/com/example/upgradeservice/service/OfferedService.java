package com.example.upgradeservice.service;

import com.example.upgradeservice.exception.InvalidDateException;
import com.example.upgradeservice.exception.InvalidEntityException;
import com.example.upgradeservice.exception.InvalidOutPutException;
import com.example.upgradeservice.model.Offered;
import com.example.upgradeservice.model.order.Ordered;
import com.example.upgradeservice.model.order.OrderedStatus;
import com.example.upgradeservice.repository.OfferedRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferedService {
    final OfferedRepo offeredRepo;
    final ReportService reportService;

    public OfferedService(OfferedRepo offeredRepo, ReportService reportService) {
        this.offeredRepo = offeredRepo;
        this.reportService = reportService;
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
}
