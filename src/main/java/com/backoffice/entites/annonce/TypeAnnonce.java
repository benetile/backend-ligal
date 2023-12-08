package com.backoffice.entites.annonce;

import com.backoffice.entites.Photo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "type_announce")
public class TypeAnnonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_announce_id")
    private Long id;

    private String nom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateDeCreation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateModifier;

    @OneToMany(targetEntity = Annonce.class, cascade = CascadeType.MERGE, mappedBy = "typeAnnonce")
    //@JsonIgnoreProperties(value ="typeAnnonce")
    @JsonIgnore
    private List<Annonce> annonces;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
   // @JsonIgnore
    @JsonIgnoreProperties(value = "typeAnnonce")
    private Photo photo;


}
