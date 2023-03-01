package com.example.upgradeservice.service;

import com.example.upgradeservice.model.services.UnderService;
import com.example.upgradeservice.repository.UnderServicesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjobService {

    private final UnderServicesRepo underServicesRepo;
    public SubjobService(UnderServicesRepo underServicesRepo) {
        this.underServicesRepo = underServicesRepo;
    }

    public void createUnderServices(UnderService underService){
        underServicesRepo.save(underService);
    }

    public UnderService readById(Long id){
        return underServicesRepo.readUnderServiceById(id);
    }

    public UnderService readByName(String name){

        return underServicesRepo.readUnderServiceByName(name);
    }

    public List<UnderService>readByService(Long id){
        return underServicesRepo.readUnderServiceByServicesId(id);
    }

    public void delete(UnderService underService){
        underServicesRepo.delete(underService);
    }
}
