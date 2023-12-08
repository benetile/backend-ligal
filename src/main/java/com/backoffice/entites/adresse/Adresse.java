package com.backoffice.entites.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "adresse")
public class Adresse {

    @Id
    @Column(name = "adresse_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "numero")
    private String numero;

    @ManyToOne//(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "commune_id")
    @JsonIgnoreProperties(value = "adresses")
    private Commune commune;

    @ManyToOne//(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "ville_id")
    @JsonIgnoreProperties(value = "adresses")
    private Ville ville;


    @Column(name = "pays")
    public String pays;

}
