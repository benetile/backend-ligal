package com.backoffice.entites.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    private String nom;

    @OneToMany(targetEntity = Departement.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "region")
    @JsonIgnoreProperties(value = "region")
    private Set<Departement> departements;
}
