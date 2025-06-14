package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestPostulantUpdateInput implements Serializable {

    private boolean status;
    private String observation;
    private long idRequestPostulant;
}
