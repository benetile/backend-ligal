package com.backoffice.entites;

import com.backoffice.entites.annonce.Annonce;
import com.backoffice.entites.annonce.TypeAnnonce;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String nom;

    @Lob
    @Column(name = "file")
    private byte[] file;

    /**@Lob
    private MultipartFile data;*/

    private String extension;

    private Date dateDeCreation;

    private Boolean status;

    private String repertoire;

    private String url;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "announce_id")
    @JsonIgnoreProperties(value = "photos")
    private Annonce annonce;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "actualite_id")
    @JsonIgnoreProperties(value = "photo")
    private Actualite actualite;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "type_announce_id")
    @JsonIgnoreProperties(value = "photo")
    private TypeAnnonce typeAnnonce;


}
