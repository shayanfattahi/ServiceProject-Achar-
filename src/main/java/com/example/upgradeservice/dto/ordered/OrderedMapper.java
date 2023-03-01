package com.example.upgradeservice.dto.ordered;
import com.example.upgradeservice.model.order.Ordered;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper
public interface OrderedMapper {

    OrderedMapper INSTANCE = Mappers.getMapper(OrderedMapper.class);

    Ordered dtoToModel(OrderedDto orderedDto);

    List<GetOrderedDto> modelToGetDto(List<Ordered> ordered);
}
