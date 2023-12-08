package com.backoffice.entites.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "departement")
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departement_id")
    private Long id;

    private String nom;

    @OneToMany(targetEntity = Ville.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "departement")
    @JsonIgnoreProperties(value = "departement")
    private List<Ville> villes;

    @ManyToOne//(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties(value = "departements")
    private Region region;

    private String code;
}
