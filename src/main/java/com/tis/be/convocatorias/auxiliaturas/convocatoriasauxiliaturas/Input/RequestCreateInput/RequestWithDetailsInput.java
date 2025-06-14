package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.RequestCreateInput;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RequestWithDetailsInput implements Serializable {

    private String note, description;
    private long announcement;
    private List<RequestDetailCreateInput> requests;
}
