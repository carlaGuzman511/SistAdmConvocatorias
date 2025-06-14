package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentInput implements Serializable {

    private String name, note;
    private long announcement;
}
