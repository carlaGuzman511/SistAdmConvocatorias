package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogInput implements Serializable {

    private String newField, oldField;

    private long user, eventLog;
}
