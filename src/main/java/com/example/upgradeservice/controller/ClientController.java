package com.example.upgradeservice.controller;

import com.example.upgradeservice.dto.client.ClientDto;
import com.example.upgradeservice.dto.client.ClientMapper;
import com.example.upgradeservice.dto.client.GetClientDto;
import com.example.upgradeservice.model.users.Client;
import com.example.upgradeservice.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Controller
@RequestMapping("/Client")
public class ClientController{
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
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

    @GetMapping("/logIn/{email}/{pass}")
//    @PreAuthorize("hasRole('Client')")
    public GetClientDto logIn(@PathVariable String email , @PathVariable String pass){
        Optional<Client> client = clientService.signIn(email, pass);
        return modelToGetDto(client.get());
    }

    @PutMapping("/changePass/{email}/{pass}/{passNew}")
    public String changePass(@PathVariable String email , @PathVariable String pass , @PathVariable String passNew ){
        clientService.changePass(email , pass , passNew);
        return "ok";
    }

    @PutMapping("/changingpass")
//    @PreAuthorize("hasRole('CLIENT')")
    public String changePass(){
        Client client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        client.setPass("manDeg*!!!");
        return "ok";
    }
}
