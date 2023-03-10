package com.example.upgradeservice.controller;

import com.example.upgradeservice.model.Offered;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.model.users.Technician;
import com.example.upgradeservice.service.OfferedService;
import com.example.upgradeservice.service.ReportService;
import com.example.upgradeservice.service.TechnicianService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/Offered")
public class OfferedController {

    private final OfferedService offeredService;
    private final ReportService reportService;
    private final TechnicianService technicianService;

    public OfferedController(OfferedService offeredService, ReportService reportService, TechnicianService technicianService) {
        this.offeredService = offeredService;
        this.reportService = reportService;
        this.technicianService = technicianService;
    }

    @PostMapping("/createOffered/{orderId}")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public void createOffered(@RequestBody Offered offered , @PathVariable Long orderId){
        Technician technician = (Technician) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        offered.setOrdered(reportService.readById(orderId).get());
        offered.setClient(reportService.readById(orderId).get().getClient());
        offered.setUnderService(reportService.readById(orderId).get().getUnderService());
        offered.setTechnician(technician);
        offeredService.creatOffered(offered);
    }

    @GetMapping("/offeredSortByPrice/{orderedId}")
    @PreAuthorize("hasRole('CLIENT')")

    public List<Offered> readOfferedSortByPrice(@PathVariable Long orderedId) {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return offeredService.readOfferedSortByPrice(client.getId() , orderedId);
    }

    @GetMapping("/offeredSortByTechnician")
    @PreAuthorize("hasRole('CLIENT')")
    public List<Offered> readTopTechnician() {
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return offeredService.readTopTechnician(client.getId());
    }

    @PutMapping("/acceptOffered/{offeredId}")
    @PreAuthorize("hasRole('CLIENT')")
    public void acceptOffered(@PathVariable Long offeredId ){
        offeredService.acceptOffered(offeredId);
    }
}
