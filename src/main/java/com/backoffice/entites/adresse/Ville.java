package com.backoffice.entites.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ville")
@Getter
@Setter
@NoArgsConstructor
public class Ville {
    @Id
    @Column(name = "ville_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    private String nom;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "insee")
    private String insee;

    private String libelle;

   /* @ManyToOne(fetch = FetchType.LAZY, optional = false)//(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "province_id")
    @JsonIgnoreProperties(value = "villes")
    private Province province;*/

    private Double longitude;

    private Double latitude;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)//(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "departement_id")
    @JsonIgnoreProperties(value = "villes")
    private Departement departement;

    @OneToMany(targetEntity = Commune.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ville")
    @JsonIgnoreProperties(value = "ville")
    private List<Commune> communes;

}
