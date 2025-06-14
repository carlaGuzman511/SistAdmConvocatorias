package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class RequirementInput implements Serializable {

    private int itemsQuantity, assignedItems;
    private long announcement, auxiliary;

}
