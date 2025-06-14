package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResultFinalTeachingInput implements Serializable {

    private float scoreTotal, scoreMerits, scoreTeaching, scoreWrittenTeaching, scoreOralTeaching;
    private boolean status;
    private long auxiliary, announcement, postulant;

}
