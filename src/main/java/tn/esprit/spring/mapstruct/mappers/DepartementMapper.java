package tn.esprit.spring.mapstruct.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tn.esprit.spring.dto.DepartementDTO;
import tn.esprit.spring.entities.Departement;

@Mapper(componentModel = "spring")
public interface DepartementMapper {
    //Get Instance of class
    DepartementMapper INSTANCE = Mappers.getMapper(DepartementMapper.class);

    Departement toEntity(DepartementDTO departementDTO);
    DepartementDTO toDTO(Departement departement);
}
