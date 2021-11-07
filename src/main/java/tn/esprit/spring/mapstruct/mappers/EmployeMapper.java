package tn.esprit.spring.mapstruct.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tn.esprit.spring.dto.EmployeDTO;
import tn.esprit.spring.entities.Employe;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    //Get Instance of class
    EmployeMapper INSTANCE = Mappers.getMapper(EmployeMapper.class);

    Employe toEntity(EmployeDTO enployeDTO);
    EmployeDTO toDTO(Employe employe);
}
