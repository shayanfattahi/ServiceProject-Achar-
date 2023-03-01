package com.example.upgradeservice.dto.technician;
import com.example.upgradeservice.model.users.Technician;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface TechnicianMapper {

    TechnicianMapper INSTANCE = Mappers.getMapper(TechnicianMapper.class);

    Technician dtoToModel(TechnicianDto technicianDto);

    GetTechnicianDto modelToGetDto(Technician technician);
}
