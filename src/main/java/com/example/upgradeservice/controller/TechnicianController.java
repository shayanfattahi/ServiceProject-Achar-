package com.example.upgradeservice.controller;

import com.example.upgradeservice.dto.client.ClientDto;
import com.example.upgradeservice.dto.client.ClientMapper;
import com.example.upgradeservice.dto.client.GetClientDto;
import com.example.upgradeservice.dto.technician.GetTechnicianDto;
import com.example.upgradeservice.dto.technician.TechnicianDto;
import com.example.upgradeservice.dto.technician.TechnicianMapper;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.model.users.TecStatus;
import com.example.upgradeservice.model.users.Technician;
import com.example.upgradeservice.service.TechnicianService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Controller
@RequestMapping("/Technician")
public class TechnicianController {

    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    private Technician dtoToModelWithMapStruct(TechnicianDto technicianDto) {
        return TechnicianMapper.INSTANCE.dtoToModel(technicianDto);
    }

    private GetTechnicianDto modelToGetDto(Technician technician){
        return TechnicianMapper.INSTANCE.modelToGetDto(technician);
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody TechnicianDto technicianDto){
        technicianDto.setTecStatus(TecStatus.NEW);
        technicianService.createTechnician(dtoToModelWithMapStruct(technicianDto));
        return "ok";
    }

    @PostMapping("/logIn")
    public GetTechnicianDto logIn(@RequestBody Technician technician){
        return modelToGetDto(technicianService.signIn(technician.getEmail() , technician.getPass()).get());
    }

    @PutMapping("/changePass/{email}/{pass}/{passNew}")
    public void changePass(@PathVariable String email , @PathVariable String pass , @PathVariable String passNew ){
        technicianService.changePass(email , pass , passNew);
    }
}
