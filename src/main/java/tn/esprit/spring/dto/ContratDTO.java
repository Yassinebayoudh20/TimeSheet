package tn.esprit.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tn.esprit.spring.entities.Employe;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratDTO {
    @JsonProperty("reference")
    private int reference;

    @JsonProperty("dateDebut")
    private Date dateDebut;

    @JsonProperty("typeContrat")
    private String typeContrat;

    @JsonProperty("salaire")
    private float salaire;

    @JsonProperty("employe")
    private Employe employe;



}
