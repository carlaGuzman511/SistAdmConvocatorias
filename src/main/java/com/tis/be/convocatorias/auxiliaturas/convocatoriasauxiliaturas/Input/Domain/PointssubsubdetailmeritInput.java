package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class PointssubsubdetailmeritInput implements Serializable {

    private String description;
    private int points;
    private long subsubdetailmerit;

}
