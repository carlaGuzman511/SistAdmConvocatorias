package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DocsToPresentCreateInput implements Serializable {

    private String name, note;
    private List<DocDetailCreateInput> docs;
}
