package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.DocumentCreateInput;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.DocDetailCreateInput;
import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.DocsToPresentCreateInput;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DocumentWithDetailsCreateInput implements Serializable {

    private String name, note;
    private long announcement;
    private List<DocDetailCreateInput> docs;
}
