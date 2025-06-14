package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class RequestInput implements Serializable {

    private String description, note;
    private long announcement;

}
