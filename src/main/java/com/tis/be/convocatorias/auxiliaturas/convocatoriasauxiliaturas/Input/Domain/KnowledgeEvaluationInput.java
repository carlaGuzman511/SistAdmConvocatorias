package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeEvaluationInput implements Serializable {

    private String description;
    private int percentage;
    private long knowledge;

}
