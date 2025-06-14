package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultDetailMeritEvaluationInput implements Serializable {

    private List<ResultSubdetailMeritEvaluationInput> scores;
    private long detailMerit, label, resultmeritevaluation;

}
