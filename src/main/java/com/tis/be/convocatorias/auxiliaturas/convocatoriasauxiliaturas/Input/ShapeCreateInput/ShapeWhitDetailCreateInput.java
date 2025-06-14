package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.ShapeCreateInput;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput.DetailShapeCreateInput;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShapeWhitDetailCreateInput implements Serializable {

    private String description;
    private long announcement;
    private List<DetailShapeCreateInput> shapes;
}
