package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class DetailShapeInput implements Serializable {

    private String description;
    private long shape;
}
