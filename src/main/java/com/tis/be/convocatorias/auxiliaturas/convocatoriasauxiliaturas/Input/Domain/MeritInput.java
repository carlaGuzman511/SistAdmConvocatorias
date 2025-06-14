package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class MeritInput implements Serializable {

    private String description, note;
    private int baseScore, finalScore;
    private long announcement;

}
