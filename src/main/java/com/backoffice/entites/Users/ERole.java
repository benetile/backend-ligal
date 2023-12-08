package com.backoffice.entites.Users;

import com.backoffice.entites.annonce.Annonce;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class ERole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long role_id;

    @Column(name = "nom")
    private String nom;

    /**@OneToMany(targetEntity = User.class, cascade = CascadeType.ALL, mappedBy = "eRole")
    @JsonIgnoreProperties(value ="eRole")*/

    /*@JoinTable(name = "users_role",joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))*/
    //List<User> users;
}
