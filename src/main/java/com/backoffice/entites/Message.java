package com.backoffice.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String destinataire;

    private String expediteur;

    private Long idAnnonce;

    @Column(name = "contenue",length = 1500)
    private String contenue;

    private Boolean lu;

    private Date date;

    private Date dateLecture;

    private Boolean programmer;

    /**@ManyToOne//(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "conversation_id")
    @JsonIgnoreProperties(value = "messages")
    private Conversation conversation;*/


}
