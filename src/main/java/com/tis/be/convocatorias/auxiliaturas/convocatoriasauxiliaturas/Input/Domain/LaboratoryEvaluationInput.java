package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class LaboratoryEvaluationInput implements Serializable {

    private int percentage;
    private long auxiliary, thematic, knowledge;

}
