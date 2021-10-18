package tn.esprit.spring.mapstruct.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tn.esprit.spring.dto.MissionDTO;
import tn.esprit.spring.entities.Mission;

@Mapper(componentModel = "spring")
public interface MissionMapper {
    //Get Instance of class
    MissionMapper INSTANCE = Mappers.getMapper(MissionMapper.class);

    Mission toEntity(MissionDTO missionDTO);
    MissionDTO toDTO(Mission mission);
}
