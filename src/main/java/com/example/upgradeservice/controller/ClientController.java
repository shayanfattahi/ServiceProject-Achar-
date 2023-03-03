package com.example.upgradeservice.controller;

import com.example.upgradeservice.dto.client.ClientDto;
import com.example.upgradeservice.dto.client.ClientMapper;
import com.example.upgradeservice.dto.client.GetClientDto;
import com.example.upgradeservice.exception.InvalidPassException;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/Client")
public class ClientController{
    private final ClientService clientService;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientController(ClientService clientService, BCryptPasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    private Client dtoToModelWithMapStruct(ClientDto clientDto) {
        return ClientMapper.INSTANCE.dtoToModel(clientDto);
    }

    private GetClientDto modelToGetDto(Client client){
        return ClientMapper.INSTANCE.modelToGetDto(client);
    }

    @PostMapping("/register")
    public ClientDto register(@Valid @RequestBody ClientDto clientDto){
        clientService.createClient(dtoToModelWithMapStruct(clientDto));
        return clientDto;
    }

    @PutMapping("/changingpass/{pass}")
    @PreAuthorize("hasRole('CLIENT')")
    public String changePass(@PathVariable String pass){
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")){
            throw new InvalidPassException();
        }
        client.setPass(passwordEncoder.encode(pass));
        clientService.create(client);
        return "ok";
    }
}
