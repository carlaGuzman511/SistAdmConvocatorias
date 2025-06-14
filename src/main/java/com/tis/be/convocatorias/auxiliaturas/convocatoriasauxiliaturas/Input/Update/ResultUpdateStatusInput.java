package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultUpdateStatusInput implements Serializable {
    
    private boolean status;
    private long idResult;
    
}
