package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostulantInput implements Serializable {

    private boolean status;
    private long person, career;

}
