package com.example.upgradeservice.controller;

import com.example.upgradeservice.model.Offered;
import com.example.upgradeservice.service.OfferedService;
import com.example.upgradeservice.service.ReportService;
import com.example.upgradeservice.service.TechnicianService;
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

    @PostMapping("/createOffered/{orderId}/{email}")
    public void createOffered(@RequestBody Offered offered , @PathVariable Long orderId , @PathVariable String email){
        offered.setOrdered(reportService.readById(orderId).get());
        offered.setClient(reportService.readById(orderId).get().getClient());
        offered.setUnderService(reportService.readById(orderId).get().getUnderService());
        offered.setTechnician(technicianService.findByEmail(email));
        offeredService.creatOffered(offered);
    }

    @GetMapping("/offeredSortByPrice/{clientId}/{orderedId}")
    public List<Offered> readOfferedSortByPrice(@PathVariable Long clientId , @PathVariable Long orderedId) {
        return offeredService.readOfferedSortByPrice(clientId , orderedId);
    }

    @GetMapping("/offeredSortByTechnician/{clientId}")
    public List<Offered> readTopTechnician(@PathVariable Long clientId) {
        return offeredService.readTopTechnician(clientId);
    }

    @PutMapping("/acceptOffered/{offeredId}")
    public void acceptOffered(@PathVariable Long offeredId ){
        offeredService.acceptOffered(offeredId);
    }
}
