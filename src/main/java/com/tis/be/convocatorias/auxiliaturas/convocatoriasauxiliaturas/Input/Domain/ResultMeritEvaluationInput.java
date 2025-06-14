package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResultMeritEvaluationInput implements Serializable {

    private float score;
    private int percentage;
    private long label;
}
