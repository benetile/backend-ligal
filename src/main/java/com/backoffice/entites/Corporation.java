package com.backoffice.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

//@Entity
//@Table(name = "corporation")
public class Corporation {
   /** @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corporation_id")
    private long id;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "status")
    private String status;
    @Column(name = "siret")
    private String siret;
    @Column(name = "ca")
    private String ca;
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "telephone_id")
    /**@JoinTable(name = "corporation_telephone", joinColumns = @JoinColumn(name = "telephone_id"),
            inverseJoinColumns = @JoinColumn(name = "corporation_id"))*/
  //  private Telephone telephone;

    //@OneToOne(cascade = CascadeType.ALL, mappedBy = "corporation")
    /**@JoinTable(name = "corporation_adresse", joinColumns = @JoinColumn(name = "adresse_id"),
            inverseJoinColumns = @JoinColumn(name = "corporation_id"))*/
   /** @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    public Adresse adresse;

   // @OneToMany(fetch = FetchType.LAZY)
    //@JoinTable(name = "corporation_accounts", joinColumns = @JoinColumn(name = "account_id"),
       //     inverseJoinColumns = @JoinColumn(name = "corporation_id"))
    //@JsonIgnoreProperties(value ="corporation")
    @OneToMany(targetEntity = Account.class, cascade = CascadeType.MERGE, mappedBy = "corporation")
    @JsonIgnoreProperties(value = "corporation")
    public List<Account> accounts;

    public Corporation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccounts(Account account){
        this.accounts.add(account);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/
}
