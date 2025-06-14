package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class RequestPostulantInput implements Serializable {

    private boolean status;
    private String observation;
    private long idrequestdetail, idpostulant, iduser;

}
