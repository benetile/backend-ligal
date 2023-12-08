package com.backoffice.entites.filtre;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Filtre {

    private Parametre nom;
    private Parametre dateDePublication;
    private Parametre status;
}
