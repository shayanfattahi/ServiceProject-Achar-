package com.example.upgradeservice.controller;

import com.example.upgradeservice.dto.ordered.GetOrderedDto;
import com.example.upgradeservice.dto.ordered.OrderedDto;
import com.example.upgradeservice.dto.ordered.OrderedMapper;
import com.example.upgradeservice.dto.technician.GetTechnicianDto;
import com.example.upgradeservice.dto.technician.TechnicianDto;
import com.example.upgradeservice.dto.technician.TechnicianMapper;
import com.example.upgradeservice.model.order.Ordered;
import com.example.upgradeservice.model.users.TecStatus;
import com.example.upgradeservice.model.users.Technician;
import com.example.upgradeservice.service.TechnicianService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/Technician")
public class TechnicianController {

    private final TechnicianService technicianService;
    private final BCryptPasswordEncoder passwordEncoder;


    public TechnicianController(TechnicianService technicianService, BCryptPasswordEncoder passwordEncoder) {
        this.technicianService = technicianService;
        this.passwordEncoder = passwordEncoder;
    }

    private Technician dtoToModelWithMapStruct(TechnicianDto technicianDto) {
        return TechnicianMapper.INSTANCE.dtoToModel(technicianDto);
    }

    private GetTechnicianDto modelToGetDto(Technician technician){
        return TechnicianMapper.INSTANCE.modelToGetDto(technician);
    }

    private Ordered dtoToModelWithMapStruct(OrderedDto orderedDto) {
        return OrderedMapper.INSTANCE.dtoToModel(orderedDto);
    }

    private List<GetOrderedDto> modelToGetDto(List<Ordered> ordered){
        return OrderedMapper.INSTANCE.modelToGetDto(ordered);
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody TechnicianDto technicianDto){
        technicianDto.setTecStatus(TecStatus.NEW);
        technicianService.createTechnician(dtoToModelWithMapStruct(technicianDto));
        return "ok";
    }

    @PutMapping("/changingpass")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public String changePass(@RequestBody String pass){
        Technician technician = (Technician) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        technician.setPass(passwordEncoder.encode(technician.getPassword()));
        technicianService.create(technician);
        return "ok";
    }


    @PutMapping("/readTechnicianOrdered")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public List<GetOrderedDto> readTechnicianOrdered(){
        Technician technician = (Technician) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelToGetDto(technicianService.getOrderedOfTechnician(technician.getId()));
    }
}
