package com.backoffice.entites.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "commune")
@NoArgsConstructor
@Getter
@Setter
public class Commune {
    @Id
    @Column(name = "commune_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    private String nom;

    @Column(name = "code_insee")
    private String codeInsee;

    private Double longitude;

    private Double latitude;

    private int nombre;

    @ManyToOne//(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "ville_id")
    @JsonIgnoreProperties(value = "communes")
    private Ville ville;

}


