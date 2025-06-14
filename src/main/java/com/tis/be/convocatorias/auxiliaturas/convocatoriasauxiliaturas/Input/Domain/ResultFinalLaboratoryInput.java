package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResultFinalLaboratoryInput implements Serializable {

    private float scoreTotal, scoreMerits, scoreLaboratory;
    private boolean status;
    private long auxiliary, announcement, postulant;

}
