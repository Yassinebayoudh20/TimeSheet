package tn.esprit.spring.mapstruct.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tn.esprit.spring.dto.EmployeDTO;
import tn.esprit.spring.dto.EntrepriseDTO;
import tn.esprit.spring.entities.Entreprise;

@Mapper(componentModel = "spring")
public interface EntrepiseMapper {

    //Get Instance of class
    EntrepiseMapper INSTANCE = Mappers.getMapper(EntrepiseMapper.class);

    Entreprise toEntity(EntrepriseDTO enployeDTO);
    EmployeDTO toDTO(Entreprise entreprise);
}
