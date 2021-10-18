package tn.esprit.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.spring.entities.Departement;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntrepriseDTO {

    private int id;
    private String name;
    private String raisonSocial;
    private List<Departement> departements = new ArrayList<>();

}
