package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResultWrittenTeachingEvaluationInput implements Serializable {


    private double score;
    private long knowledgeEvaluation, postulant, auxiliary;

}
