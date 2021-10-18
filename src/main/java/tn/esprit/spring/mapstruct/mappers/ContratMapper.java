package tn.esprit.spring.mapstruct.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tn.esprit.spring.dto.ContratDTO;
import tn.esprit.spring.entities.Contrat;

@Mapper(componentModel = "spring")
public interface ContratMapper {

    //Get Instance of class
    ContratMapper INSTANCE = Mappers.getMapper(ContratMapper.class);

    Contrat contratDTOToContrat(ContratDTO contrat);
    ContratDTO contratToContratDto(Contrat contrat);
}
