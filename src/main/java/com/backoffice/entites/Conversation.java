package com.backoffice.entites;

import com.backoffice.entites.annonce.Annonce;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
/*@Entity
@Table(name = "conversation")*/
public class Conversation {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")*/
    private Long id;

    private String titre;


   /* @OneToMany(targetEntity = Message.class, cascade = CascadeType.MERGE, mappedBy = "conversation")
    @JsonIgnoreProperties(value ="conversation")*/
    private List<Message> messages;

    /*@ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "announce_id")
    @JsonIgnoreProperties(value = "conversations")*/
    private Annonce annonce;


    /*@ManyToMany
    @JoinTable( name = "T_Account_Conversation_Associations",
            joinColumns = @JoinColumn( name = "conversation_id" ),
            inverseJoinColumns = @JoinColumn( name = "account_id" ))
    @JsonIgnoreProperties(value = "conversations")
    //@JsonIgnore
    private List<Account>accounts;*/
}
