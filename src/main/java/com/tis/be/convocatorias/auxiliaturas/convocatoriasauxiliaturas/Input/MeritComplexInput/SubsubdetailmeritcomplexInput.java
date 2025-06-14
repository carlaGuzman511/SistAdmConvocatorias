package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SubsubdetailmeritcomplexInput implements Serializable {

    private String subdetail;
    private int percent;
    private List<PointssubsubdetailmeritcomplexInput> pointDetail;
}
