package tn.esprit.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeDTO {

    private int id;
    private String prenom;
    private String nom;
    private String email;
    private boolean isActif;
    private Role role;
    private List<Departement> departements;
    private Contrat contrat;
    private List<Timesheet> timesheets;
}
