package com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Input.MeritComplexInput;


import com.tis.be.convocatorias.auxiliaturas.convocatoriasauxiliaturas.Model.Domain.SubdetailMerit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DetailmeritcomplexInput implements Serializable {

    private String name, merit;
    private int percentage;
    private List<SubdetailmeritcomplexInput> details;
}
