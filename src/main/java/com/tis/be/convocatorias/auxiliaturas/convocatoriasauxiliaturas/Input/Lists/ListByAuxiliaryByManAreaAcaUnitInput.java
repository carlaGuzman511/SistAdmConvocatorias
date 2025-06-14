package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListByAuxiliaryByManAreaAcaUnitInput implements Serializable {

    private long idmanagement, idarea, idacademicunit;
}
