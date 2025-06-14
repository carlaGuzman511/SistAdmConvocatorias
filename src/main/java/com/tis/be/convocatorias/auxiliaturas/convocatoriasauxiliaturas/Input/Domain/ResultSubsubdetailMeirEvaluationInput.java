package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResultSubsubdetailMeirEvaluationInput implements Serializable {

    private float score;
    private String observations;
    private long subsubdetailmerit, resultsubdetailmeritevaluation;


}
