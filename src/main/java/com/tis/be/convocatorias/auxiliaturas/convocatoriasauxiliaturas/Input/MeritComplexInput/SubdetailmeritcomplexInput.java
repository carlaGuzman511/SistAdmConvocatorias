package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubsubdetailMerit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SubdetailmeritcomplexInput implements Serializable {

    private String detail;
    private int percent;
    private List<SubsubdetailmeritcomplexInput> subdetails;
}
