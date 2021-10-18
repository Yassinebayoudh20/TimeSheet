package tn.esprit.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartementDTO {

    private int id;
    private String name;
    private List<Employe> employes;
    private List<Mission> missions;
    private Entreprise entreprise;
}
