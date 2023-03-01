package com.example.upgradeservice.service;

import com.example.upgradeservice.exception.DuplicateUserException;
import com.example.upgradeservice.exception.InvalidOutPutException;
import com.example.upgradeservice.model.services.Services;
import com.example.upgradeservice.model.services.UnderService;
import com.example.upgradeservice.repository.ServiceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final ServiceRepo serviceRepo;
    private final SubjobService subjobService;
    public JobService(ServiceRepo serviceRepo, SubjobService subjobService) {
        this.serviceRepo = serviceRepo;
        this.subjobService = subjobService;
    }

    public Services readService(String serviceName){
        return serviceRepo.readServicesByName(serviceName);
    }

    public void createServices(Services services){
        if (serviceRepo.readServicesByName(services.getName()) != null){
            throw new DuplicateUserException();
        }
        serviceRepo.save(services);
    }

    public void deleteService(String name){
        Services services = serviceRepo.readServicesByName(name);
        List<UnderService> underServices = subjobService.readByService(services.getId());
        for (UnderService underservice: underServices) {
            underservice.setServices(null);
            subjobService.createUnderServices(underservice);
        }
        serviceRepo.delete(services);
    }

    public Services readById(Long id){
        if (serviceRepo.readServicesById(id) == null){
            throw new InvalidOutPutException();
        }
        return serviceRepo.readServicesById(id);
    }

}
