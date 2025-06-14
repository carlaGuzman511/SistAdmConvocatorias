package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update;


import lombok.Data;

import java.io.Serializable;

@Data
public class PostulantUpdateStatusInput implements Serializable {

    private boolean status;
    private long idPostulant;

}
