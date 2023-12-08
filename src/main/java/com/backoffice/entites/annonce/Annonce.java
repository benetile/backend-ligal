package com.backoffice.entites.annonce;

import com.backoffice.entites.Account;
import com.backoffice.entites.Conversation;
import com.backoffice.entites.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "announce")
public class Annonce {
    @Id
    @Column(name = "announce_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titre")
    private String titre;
    @Column(name = "reference")
    private String reference;
    @Column(name = "description",length = 1500)
    private String description;
    @Column(name = "montant")
    private BigDecimal montant;
    @Column(name = "devise")
    private String devise;

    @Column(name = "status")
    private String status;
    @Column(name = "disponible")
    private Boolean disponible;

    @Column(name = "commune")
    private String commune;
    @Column(name = "ville")
    private String ville;
    @Column(name = "departement")
    private String departement;
    @Column(name = "date")
    private Date date;
    @Column(name = "vue")
    private Long vue;

    @ManyToOne//(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = "annonces")
    private Account account;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "type_announce_id")
    @JsonIgnoreProperties(value = "annonces")
    private TypeAnnonce typeAnnonce;

    @OneToMany(targetEntity = Photo.class, cascade = CascadeType.MERGE, mappedBy = "annonce")
    @JsonIgnoreProperties(value = "annonce")
    private List<Photo> photos;

    /**@OneToMany(targetEntity = Conversation.class, cascade = CascadeType.MERGE, mappedBy = "annonce")
   // @JsonIgnore
    @JsonIgnoreProperties(value = "annonce")
    private List<Conversation> conversations;*/




}
