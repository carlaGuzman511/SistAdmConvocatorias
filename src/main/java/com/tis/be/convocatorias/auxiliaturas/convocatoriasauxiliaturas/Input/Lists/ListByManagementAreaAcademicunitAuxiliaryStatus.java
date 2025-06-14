package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists;


import lombok.Data;

import java.io.Serializable;

@Data
public class ListByManagementAreaAcademicunitAuxiliaryStatus implements Serializable {

    private long idmanagement, idarea, idacademicunit, idauxiliary;
    private boolean status;
}
