package com.example.upgradeservice.controller;

import com.example.upgradeservice.model.services.UnderService;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.model.users.TecStatus;
import com.example.upgradeservice.model.users.Technician;
import com.example.upgradeservice.service.JobService;
import com.example.upgradeservice.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Controller
@RequestMapping("/Manager")
public class ManagerController {

    private final ManagerService managerService;
    private final JobService jobService;

    public ManagerController(ManagerService managerService, JobService jobService) {
        this.managerService = managerService;
        this.jobService = jobService;
    }

    @PostMapping("/registerService")
    public String registerService(@RequestBody String name){
        managerService.createServices(name);
        return "ok";
    }

    @PostMapping("/registerUnderService/{serviceID}")
    public String registerUnderService(@RequestBody UnderService underService , @PathVariable Long serviceID){
        underService.setServices(jobService.readById(serviceID));
        managerService.createUnderService(underService);
        return "ok";
    }

    @PutMapping("/updateUnderService")
    public UnderService editUnderServices(@RequestBody UnderService underService){
        managerService.editUnderServices(underService.getName() , underService.getText() , underService.getPrices());
        return underService;
    }

    @PutMapping("/changeStatusToActive/{email}")
    public String changeStatusToActive(@PathVariable String email){
        managerService.changeStatusToActive(email);
        return "ok";
    }

    @DeleteMapping("/deleteServices/{name}")
    public void deleteService(@PathVariable String name){
        managerService.deleteService(name);
    }

    @PutMapping("/addTechnicianToUnderservice/{email}/{name}")
    public void addTechnicianToUnderservice(@PathVariable String email, @PathVariable String name ){
        managerService.addTechnicianToUnderService(email , name);
    }

    @DeleteMapping("/deleteTechnicianAndUnderService/{email}/{name}")
    public void deleteTechnicianAndUnderService(@PathVariable String email, @PathVariable String name ){
        managerService.deleteTechnicianAndUnderService(email , name);
    }

    @GetMapping("/getClientByName/{name}")
    public List<Client> getClientByName(@PathVariable String name){
        return managerService.getClientByName(name);
    }

    @GetMapping("/getTechnicianByPoint")
    public List<Technician> getTechnicianByPoint(){
        return managerService.getTechnicianByPoint();
    }

    @GetMapping("/getClientByLastName/{lastname}")
    public List<Client> getClientByLastName(@PathVariable String lastname){
        return managerService.getClientByLatName(lastname);
    }

    @GetMapping("/getClientByEmail/{email}")
    public Client getClientByEmailName(@PathVariable String email){
        return managerService.getClientByEmail(email);
    }

    @GetMapping("/getTechnicianByName/{name}")
    public List<Technician> getTechnicianByName(@PathVariable String name){
        return managerService.getTechnicianByName(name);
    }

    @GetMapping("/getTechnicianByLastName/{lastname}")
    public List<Technician> getTechnicianByLastName(@PathVariable String lastname){
        return managerService.getTechnicianByLastName(lastname);
    }

    @GetMapping("/getTechnicianByEmail/{email}")
    public Technician getTechnicianByEmail(@PathVariable String email){
        return managerService.getTechnicianByEmail(email);
    }

    @GetMapping("/getTechnicianByUnderServices/{id}")
    public List<Technician> getTechnicianByUnderServices(@PathVariable Long id){
        return managerService.getTechByUnder(id);
    }


}
