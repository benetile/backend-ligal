package com.backoffice.entites.adresse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "province")
@NoArgsConstructor
@Getter()
@Setter()
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "chefLieu")
    private String chefLieu;

    @Column(name = "superficie")
    private String superficie;


}
