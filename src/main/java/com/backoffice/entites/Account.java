package com.backoffice.entites;

import com.backoffice.entites.adresse.Adresse;
import com.backoffice.entites.annonce.Annonce;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "civilite")
    private String civilite;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;

    @Column(name = "dateDeNaissance")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;
    @Column(name = "genre")
    private String genre;
    @Column(name = "email",unique = true)
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "telephone_id")
    private Telephone telephone;

    @Column(name = "user_id")
    public long idUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonIgnoreProperties(value ="account")
    private Adresse adresse;

    @OneToMany(targetEntity = Annonce.class, cascade = CascadeType.MERGE, mappedBy = "account")
    @JsonIgnoreProperties(value ="account")
    private List<Annonce> annonces;

    /**@ManyToMany
    @JoinTable(name = "T_Account_Conversation_Associations",
                joinColumns = @JoinColumn(name = "conversation_id"),
                inverseJoinColumns = @JoinColumn(name = "account_id"))
    //@JsonIgnoreProperties(value = "account")
    @JsonIgnoreProperties(value = "accounts")
    private List<Conversation> conversations;*/

}
