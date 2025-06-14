package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.AnnouncementCreateInput;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShapeCreateInput implements Serializable {

    private String description;
    private List<DetailShapeCreateInput> shapes;
}
