package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdLaboratoryevaluationPostulantInput implements Serializable {

    private long idlaboratoryevaluation, idpostulant;

}
