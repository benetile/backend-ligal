package com.backoffice.entites;

import com.backoffice.entites.annonce.Annonce;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Commentaire {

    private long commentaire_id;

    private String message;

    private Annonce annonce;
}
