package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Update;


import lombok.Data;

import java.io.Serializable;

@Data
public class RequirementUpdateAssignedItemsInput implements Serializable {

    private int assignedItems;
    private long idRequirement;
}
