package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeInput implements Serializable {

    private String description;
    private int baseScore, finalScore;
    private long announcement;

}
