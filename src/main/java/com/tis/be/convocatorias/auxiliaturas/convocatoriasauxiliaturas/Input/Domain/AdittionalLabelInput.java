package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class AdittionalLabelInput implements Serializable {

    private String field;
    private long label;

}
