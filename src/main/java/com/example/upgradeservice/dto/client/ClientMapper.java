package com.example.upgradeservice.dto.client;
import com.example.upgradeservice.model.users.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client dtoToModel(ClientDto clientDto);

    GetClientDto modelToGetDto(Client client);
}
