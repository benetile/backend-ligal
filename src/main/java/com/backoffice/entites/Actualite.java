package com.backoffice.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "actualite")
@Getter
@Setter
@NoArgsConstructor
public class Actualite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actualite_id")
    public Long id;

    @Column(name = "titre")
    public String titre;

    @Column(name = "description",length = 2000)
    public String description;

    @Column(name = "date_publication")
    public Date datePublication;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    @JsonIgnoreProperties(value = "actualite")
    private Photo photo;

}
