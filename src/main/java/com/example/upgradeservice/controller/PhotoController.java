package com.example.upgradeservice.controller;

import com.example.upgradeservice.model.PhotoTec;
import com.example.upgradeservice.model.users.Technician;
import com.example.upgradeservice.service.PhotoTecService;
import com.example.upgradeservice.service.TechnicianService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/Photo")
public class PhotoController {

    final PhotoTecService photoTecService;
    final TechnicianService technicianService;

    public PhotoController(PhotoTecService photoTecService, TechnicianService technicianService) {
        this.photoTecService = photoTecService;
        this.technicianService = technicianService;
    }

    @PostMapping("/photo/{techid}")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public void setPhoto(@ModelAttribute PhotoTec photoTec , @PathVariable Long techid){
        Technician technician = technicianService.readById(techid);
        photoTec.setTechnician(technician);
        photoTecService.insertPhoto(photoTec);
    }
}
