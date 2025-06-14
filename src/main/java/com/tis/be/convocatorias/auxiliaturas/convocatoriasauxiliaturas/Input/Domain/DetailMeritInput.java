package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class DetailMeritInput implements Serializable {

    private String name, description;
    private int percentage;
    private long merit;

}
