package com.example.upgradeservice.service;

import com.example.upgradeservice.dto.technician.GetTechnicianDto;
import com.example.upgradeservice.exception.DuplicateUserException;
import com.example.upgradeservice.exception.InvalidOutPutException;
import com.example.upgradeservice.model.services.Services;
import com.example.upgradeservice.model.services.UnderService;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.model.users.TecStatus;
import com.example.upgradeservice.model.users.Technician;
import com.example.upgradeservice.repository.ManagerRepo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ManagerService {
    final
    TechnicianService technicianService;
    final
    JobService servicesService;
    final
    SubjobService underServicesService;
    final
    ClientService clientService;
    final ManagerRepo managerRepo;

    public ManagerService(TechnicianService technicianService, JobService servicesService, SubjobService underServicesService, ClientService clientService, ManagerRepo managerRepo) {
        this.technicianService = technicianService;
        this.servicesService = servicesService;
        this.underServicesService = underServicesService;
        this.clientService = clientService;
        this.managerRepo = managerRepo;
    }

    public void changeStatusToActive(String email){
        if (technicianService.findByEmail(email) != null) {
            Technician technician = technicianService.findByEmail(email);
            technician.setTecStatus(TecStatus.ACTIVE);
            technicianService.create(technician);
        }else
            throw new InvalidOutPutException();
    }

    public void createServices(String serviceName){
        Services services = new Services();
        if (servicesService.readService(serviceName) == null){
            services.setName(serviceName);
            servicesService.createServices(services);
        }else{
            throw new DuplicateUserException();
        }
    }

    public void createUnderService(UnderService underService){
        if (underServicesService.readByName(underService.getName()) == null){
            underServicesService.createUnderServices(underService);
        }else
            throw new DuplicateUserException();
    }

    public void editUnderServices(String name , String text , long money){
        UnderService underService = underServicesService.readByName(name);
        if(underService == null){
            throw new InvalidOutPutException();
        }else {
            underService.setText(text);
            underService.setPrices(money);
            underServicesService.createUnderServices(underService);
        }
    }

    public void deleteService(String name){
        servicesService.deleteService(name);
    }

    public void addTechnicianToUnderService(String email , String name){
        Technician technician = technicianService.findByEmail(email);
        UnderService underService = underServicesService.readByName(name);
        if (technician.getTecStatus().equals(TecStatus.ACTIVE)) {
            Set<UnderService> underServices = technician.getUnderServices();
            underServices.add(underService);
            Set<Technician> technicians = underService.getTechnician();
            technicians.add(technician);
            technician.setUnderServices(underServices);
            underService.setTechnician(technicians);
            technicianService.updateTechnician(technician);
            underServicesService.createUnderServices(underService);
        }else
            throw new InvalidOutPutException();
    }

    public void deleteTechnicianAndUnderService(String email , String name){
        Technician technician = technicianService.findByEmail(email);
        UnderService underService = underServicesService.readByName(name);
        managerRepo.deleteUnderserviceAndTech(technician.getId(), underService.getId());
    }

//    public List<Client> getClientByName(String name){
//        return clientService.getClientByName(name);
//    }
//
//    public List<Client> getClientByLatName(String lastname){
//        return clientService.getClientByLastName(lastname);
//    }
//
//    public Client getClientByEmail(String email){
//        return clientService.getClientByEmail(email);
//    }

//    public List<Technician> getTechnicianByPoint(){
//        return technicianService.getTechnicianByPoint();
//    }
//
//    public List<Technician> getTechnicianByName(String name){
//        return technicianService.getTechnicianByName(name);
//    }
//
//    public List<Technician> getTechnicianByLastName(String lastname){
//        return technicianService.getTechnicianByLastName(lastname);
//    }
//
//    public Technician getTechnicianByEmail(String email){
//        return technicianService.getTechnicianByEmail(email);
//    }
//
//    public List<Technician> getTechnicianByUnderServices(Long id){
//        return technicianService.getTechnicianByUnderService(id);
//    }

    public List<Technician> getTechByUnder(Long id){
        return technicianService.getTechByUnderService(id);
    }
}
