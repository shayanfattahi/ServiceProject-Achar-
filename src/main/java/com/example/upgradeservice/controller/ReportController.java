package com.example.upgradeservice.controller;

import com.example.upgradeservice.dto.PayedDto;
import com.example.upgradeservice.dto.client.ClientDto;
import com.example.upgradeservice.dto.client.ClientMapper;
import com.example.upgradeservice.dto.client.GetClientDto;
import com.example.upgradeservice.dto.ordered.GetOrderedDto;
import com.example.upgradeservice.dto.ordered.OrderedDto;
import com.example.upgradeservice.dto.ordered.OrderedMapper;
import com.example.upgradeservice.model.order.Ordered;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.service.ClientService;
import com.example.upgradeservice.service.ReportService;
import com.example.upgradeservice.service.SubjobService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@RequestMapping("/Ordered")
public class ReportController {
    private final SubjobService subjobService;
    private final ReportService reportService;
    private final ClientService clientService;

    public ReportController(SubjobService subjobService, ReportService reportService, ClientService clientService) {
        this.subjobService = subjobService;
        this.reportService = reportService;
        this.clientService = clientService;
    }

    private Ordered dtoToModelWithMapStruct(OrderedDto orderedDto) {
        return OrderedMapper.INSTANCE.dtoToModel(orderedDto);
    }

    private List<GetOrderedDto> modelToGetDto(List<Ordered> ordered){
        return OrderedMapper.INSTANCE.modelToGetDto(ordered);
    }

    @PostMapping("/createOrder/{underServiceId}")
    @PreAuthorize("hasRole('CLIENT')")

    public void createOrder(@Valid @RequestBody OrderedDto orderedDto, @PathVariable String underServiceId){
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ordered ordered = dtoToModelWithMapStruct(orderedDto);
        ordered.setClient(client);
        ordered.setUnderService(subjobService.readByName(underServiceId));
        reportService.createOrder(ordered);
    }

    @GetMapping("/readLogInClientOrder/{clientId}")
    @PreAuthorize("hasRole('CLIENT')")

    public List<GetOrderedDto> readLogInClientOrder(@PathVariable Long clientId){
        return modelToGetDto(reportService.readLogInClientOrder(clientId));
    }

    @GetMapping("/readSuitableTech/{technicianId}")
    @PreAuthorize("hasRole('TENCHNICAN')")

    public List<Ordered> readSuitableTech(@PathVariable Long technicianId){
        return reportService.readSuitableTech(technicianId);
    }

    @GetMapping("/makeIsDone/{id}/{point}")
    @PreAuthorize("hasRole('CLIENT')")

    public void makeIsDone(@PathVariable Long id , double point){
        reportService.makeIsDone(id , point);
    }

    @PutMapping("/isStart/{orderedId}")
    @PreAuthorize("hasRole('CLIENT')")

    public void isStart(@PathVariable Long orderedId){
        reportService.isStarted(orderedId);
    }

    @PutMapping("/isDone/{orderedId}")
    @PreAuthorize("hasRole('CLIENT')")

    public void isDone(@PathVariable Long orderedId){
        reportService.isDone(orderedId);
    }

    @PutMapping("/isPayed/{orderedId}")
    @PreAuthorize("hasRole('CLIENT')")

    public void isPayed(@RequestBody PayedDto payedDto , @PathVariable Long orderedId){
        reportService.isPayed(orderedId , payedDto);
    }
}
