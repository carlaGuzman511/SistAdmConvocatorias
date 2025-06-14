package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.Lists;


import lombok.Data;

import java.io.Serializable;

@Data
public class ListByIdAnnouncementAuxiliaryAprovedInput implements Serializable {

    private long idannouncement, idauxiliary;
}
