package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class AcademicUnitInput implements Serializable {

    private String name;
    private long faculty;
}
