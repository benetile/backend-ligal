package com.backoffice.entites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "telephone")
@NoArgsConstructor
@Setter
@Getter
public class Telephone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "telephone_id")
    public Long id;
    @Column(name = "prefix")
    public String prefix;
    @Column(name = "code")
    public String code;
    @Column(name = "numero")
    public String numero;
    @Column(name = "pays")
    public String pays;
    @Column(name = "dateCreation")
    public Date dateDeCreation;
    @Column(name ="dateModification")
    public Date dateDeModification;

  /**  @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "account_telephone", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "telephone_id"))
    @JsonIgnore
    public Account account;*/


}
