package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResultTeachingEvaluationInput implements Serializable {


    private double score;
    private long knowledgeEvaluation, postulant;

}
